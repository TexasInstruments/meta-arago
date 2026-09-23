#!/bin/sh

# Wait for WirePlumber to be ready
i=1
while [ "$i" -le 30 ]; do
    wpctl status >/dev/null 2>&1 && break
    sleep 1
    i=$((i + 1))
done

# Additional delay for nodes to appear
sleep 2

# Find audio sink ID
SINK_ID=$(pw-cli info alsa_audio_sink 2>/dev/null | head -n 1 | awk '{print $2}')
if [ -n "$SINK_ID" ]; then
    wpctl set-default "$SINK_ID"
    echo "Set default sink to ID: $SINK_ID (alsa_audio_sink)"
else
    echo "Could not find alsa_audio_sink"
fi

# Find audio source ID
SOURCE_ID=$(pw-cli info alsa_audio_source 2>/dev/null | head -n 1 | awk '{print $2}')
if [ -n "$SOURCE_ID" ]; then
    wpctl set-default "$SOURCE_ID"
    echo "Set default source to ID: $SOURCE_ID (alsa_audio_source)"
else
    echo "Could not find alsa_audio_source"
fi

echo "Done"
