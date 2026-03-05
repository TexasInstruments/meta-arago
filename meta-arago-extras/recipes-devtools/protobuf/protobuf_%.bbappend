# Conditionally include Arago-specific customizations for flatbuffers-native
PROTOBUF_ARAGO = ""
PROTOBUF_ARAGO:arago = "protobuf-arago.inc"

require ${PROTOBUF_ARAGO}
