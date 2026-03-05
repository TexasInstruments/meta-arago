# Conditionally include Arago-specific customizations for flatbuffers-native
FLATBUFFERS_ARAGO = ""
FLATBUFFERS_ARAGO:arago = "flatbuffers-arago.inc"
require ${FLATBUFFERS_ARAGO}
