# Set extra preferences based on the product branding (AM-SDK, MC-SDK, etc)
# Examples could be different PREFERRED_PROVIDER or PREFERRED_VERSION
# As well as some other configurable aspects or variables
# Please avoid creating multiple versions of the same brand, if possible
# The policy is for the "Core" brand to be the default,
# hence this file is mostly empty and used as a template

# Raise priority for sample IPC FW images
ALTERNATIVE_PRIORITY:pn-ti-ipc-rtos = "15"

# GC320 support requires out-of-tree drivers not available in until LTS prep is
# complete. Disable for now.
# Remove gc320 - the driver fails to build with gcc12
MACHINE_FEATURES:remove = "gc320"
