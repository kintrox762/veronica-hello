#!/bin/bash
set -e

OWNER_REPO=$(gh repo view --json nameWithOwner -q .nameWithOwner)
RUN_ID=$(gh run list --limit 1 --json databaseId -q '.[0].databaseId')

echo "Latest run: $RUN_ID"

# Wait until run is finished
while :; do
  STATUS=$(gh run view "$RUN_ID" --json status -q '.status')
  echo "Status: $STATUS"
  [ "$STATUS" = "completed" ] && break
  sleep 5
done

# Download artifact
mkdir -p ~/storage/downloads
gh run download

mkdir -p scripts
cat > scripts/download-latest-apk.sh <<'EOF'
#!/bin/bash
set -e

OWNER_REPO=$(gh repo view --json nameWithOwner -q .nameWithOwner)
RUN_ID=$(gh run list --limit 1 --json databaseId -q '.[0].databaseId')

echo "Latest run: $RUN_ID"

# Wait until run is finished
while :; do
  STATUS=$(gh run view "$RUN_ID" --json status -q '.status')
  echo "Status: $STATUS"
  [ "$STATUS" = "completed" ] && break
  sleep 5
done

# Download artifact
mkdir -p ~/storage/downloads
gh run download "$RUN_ID" --dir ~/storage/downloads
echo "APK downloaded to ~/storage/downloads"
ls -lh ~/storage/downloads
