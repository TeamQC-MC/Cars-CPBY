#!/bin/bash
# Cars Remaster Launcher
# This script builds the latest code and launches the game.

echo "--- Building and Launching Cars Remaster ---"
cd "$(dirname "$0")/remaster"
java -jar ../gradle/wrapper/gradle-wrapper.jar :desktop:run --no-daemon
