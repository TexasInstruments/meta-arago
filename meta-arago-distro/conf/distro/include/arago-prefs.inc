PREFERRED_PROVIDER_virtual/gettext = "gettext"

PREFERRED_PROVIDER_virtual/kernel:append = "${ARAGO_KERNEL_SUFFIX}"

PREFERRED_RPROVIDER_libnss-mdns = "avahi-libnss-mdns"

VIRTUAL-RUNTIME_dbus = "dbus-broker"

# Setting PREFERRED_VERSIONS due to selecting a specific version of a library or
# application that does not have a GPLv3 license
PREFERRED_VERSION_crda = "3.18"
PREFERRED_VERSION_obex = "0.34"

# Bump mesa-demos to include some patches not in a tagged release
PREFERRED_VERSION_mesa-demos = "9.0.0+git"
