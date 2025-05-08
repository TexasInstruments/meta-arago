# Use the latest version of ltp-ddt for kernel versions 3.8+
PREFERRED_PROVIDER_ltp-ddt = "ltp-ddt"

PREFERRED_PROVIDER_virtual/gettext = "gettext"

PREFERRED_PROVIDER_virtual/kernel:append = "${ARAGO_KERNEL_SUFFIX}"

VIRTUAL-RUNTIME_dbus = "dbus-broker"

# Setting PREFERRED_VERSIONS due to selecting a specific version of a library or
# application that does not have a GPLv3 license
PREFERRED_VERSION_crda = "3.18"
PREFERRED_VERSION_obex = "0.34"

# Select meta-clang providers
PREFERRED_PROVIDER_llvm = "clang"
PREFERRED_PROVIDER_llvm-native = "clang-native"
PREFERRED_PROVIDER_nativesdk-llvm = "nativesdk-clang"
PROVIDES:pn-clang = "llvm"
PROVIDES:pn-clang-native = "llvm-native"
PROVIDES:pn-nativesdk-clang = "nativesdk-llvm"

# Bump mesa-demos to include some patches not in a tagged release
PREFERRED_VERSION_mesa-demos = "9.0.0+git"
