#!/usr/bin/env bash
set -euo pipefail

# Ensure Downloads is available
mkdir -p "$HOME/storage/downloads"

# Get latest run ID for this repo
RUN_ID=$(gh run list --limit 1 --json databaseId -q '.[0].databaseId')
echo "Latest run: $RUN_ID"

# Wait until the run is completed
while :; do
  STATUS=$(gh run view "$RUN_ID" --json status -q '.status')
  echo "Status: $STATUS"
  [[ "$STATUS" == "completed" ]] && break
  sleep 5
done

# (Optional) show conclusion
CONC=$(gh run view "$RUN_ID" --json conclusion -q '.conclusion')
echo "Conclusion: $CONC"

# Download all artifacts from the run into a temp dir
TMPDIR=$(mktemp -d)
echo "Downloading artifacts to: $TMPDIR"
gh run download "$RUN_ID" --dir "$TMPDIR"

# Find first APK in artifacts
APK=$(find "$TMPDIR" -type f -name "*.apk" | head -n1 || true)

if [[ -z "${APK:-}" ]]; then
  echo "ERROR: No .apk found in artifacts for run $RUN_ID"
  echo "Artifacts present:"
  gh api "repos/$(gh repo view --json nameWithOwner -q .nameWithOwner)/actions/runs/$RUN_ID/artifacts" -q '.artifacts[].name'
  exit 1
fi

# Copy as Veronica.apk to Downloads (overwrite)
DEST="$HOME/storage/downloads/Veronica.apk"
cp -f "$APK" "$DEST"

# Show result
ls -lh "$DEST"
echo "Saved APK to: $DEST"

# Try to open installer (Termux)
command -v termux-open >/dev/null 2>&1 && termux-open "$DEST" || true

# Cleanup
rm -rf "$TMPDIR"
