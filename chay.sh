#!/usr/bin/env bash
# Chay menu, du lieu mau, tung bai hoac kiem thu; chi can JDK 17+.
set -euo pipefail
cd -- "$(dirname -- "$0")"

JAVA=java
JAVAC=javac
if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/javac" ]]; then
    JAVA="$JAVA_HOME/bin/java"
    JAVAC="$JAVA_HOME/bin/javac"
fi
if ! command -v "$JAVAC" >/dev/null 2>&1 || ! command -v "$JAVA" >/dev/null 2>&1; then
    printf 'Can cai JDK 17 hoac 21 va cau hinh JAVA_HOME/PATH.\n' >&2
    exit 1
fi

MAIN=vn.edu.baitap.Main
ARGS=()
MODE="${1:-menu}"
case "$MODE" in
    menu) ;;
    demo) ARGS=(--demo) ;;
    bai1) MAIN=vn.edu.baitap.Bai1 ;;
    bai2) MAIN=vn.edu.baitap.Bai2 ;;
    bai3) MAIN=vn.edu.baitap.Bai3 ;;
    test) MAIN=vn.edu.baitap.KiemThu ;;
    *) printf 'Cach dung: bash chay.sh [menu|demo|bai1|bai2|bai3|test]\n' >&2; exit 2 ;;
esac

mkdir -p out/classes out/test-classes
"$JAVAC" -encoding UTF-8 --release 17 -d out/classes src/main/java/vn/edu/baitap/*.java
CLASSPATH=out/classes
if [[ "$MODE" == test ]]; then
    "$JAVAC" -encoding UTF-8 --release 17 -cp out/classes -d out/test-classes src/test/java/vn/edu/baitap/*.java
    CLASSPATH=out/classes:out/test-classes
fi
"$JAVA" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 \
    -cp "$CLASSPATH" "$MAIN" "${ARGS[@]}"
