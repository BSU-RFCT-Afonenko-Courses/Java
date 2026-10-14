#!/bin/sh
# Provided build helper; no student implementation is needed here.
set -u
fail() {
    printf '%s\n' "gradeapp build: $1" >&2
    exit 2
}
project_root=$(CDPATH= cd -P "$(dirname "$0")" 2>/dev/null && pwd) || fail "cannot locate project root"
for relative in pom.xml mvnw wrapper/maven-wrapper.properties; do
    [ -f "$project_root/$relative" ] || fail "required file not found: $relative"
done
command -v unzip >/dev/null 2>&1 || fail "required tool not found: unzip"
if ! command -v sha256sum >/dev/null 2>&1 && ! command -v shasum >/dev/null 2>&1; then
    fail "required tool not found: sha256sum or shasum"
fi
if ! mkdir -p "$project_root/.mvn/wrapper" 2>/dev/null ||
   ! cp "$project_root/wrapper/maven-wrapper.properties" "$project_root/.mvn/wrapper/maven-wrapper.properties" 2>/dev/null; then
    fail "cannot prepare .mvn/wrapper/maven-wrapper.properties"
fi
cd "$project_root" || fail "cannot enter project root"
exec sh "$project_root/mvnw" "$@"
