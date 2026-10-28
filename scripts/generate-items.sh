#!/usr/bin/env bash
# Regenerates src/main/resources/items/items.txt from Project Zomboid item scripts.
# Usage: scripts/generate-items.sh "<ProjectZomboid>/media/scripts/generated/items"
set -euo pipefail

ITEMS_DIR="${1:?Path to ProjectZomboid/media/scripts/generated/items is required}"
OUTPUT="$(dirname "$0")/../src/main/resources/items/items.txt"

mkdir -p "$(dirname "$OUTPUT")"
awk '$1 == "module" { module = $2 } $1 == "item" { print module "." $2 }' "$ITEMS_DIR"/*.txt \
    | tr -d '\r' | sort -u > "$OUTPUT"

echo "Generated $(wc -l < "$OUTPUT") items into $OUTPUT"
