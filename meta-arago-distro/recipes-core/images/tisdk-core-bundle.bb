SUMMARY = "Installer package for TI SDK - NOT for direct use on target"

DESCRIPTION = "This creates an installer including all the default images\
 recommended including source, binaries, filesystems, etc.\
 for TI SDK. This is meant to be used on the host system.\
"

require tisdk-core-bundle.inc

DEPLOY_SPL_NAME_omapl138 = ""
DEPLOY_SPL_NAME_k2hk = ""
DEPLOY_SPL_NAME_k2l = ""
DEPLOY_SPL_NAME_k2e = ""
# Only unset it for HS device, as k2g GP does provide MLO/SPL
DEPLOY_SPL_NAME_k2g-hs-evm = ""
DEPLOY_SPL_NAME_k3 = "tispl.bin tiboot3.bin"

DEPLOY_IMAGES_NAME_k3 = "bl31.bin bl32.bin bl31.bin.unsigned bl32.bin.unsigned"
DEPLOY_IMAGES_NAME_append_am65xx = " sysfw.itb"
DEPLOY_IMAGES_NAME_append_j7-evm = " sysfw.itb"
DEPLOY_IMAGES_NAME_append_j7-hs-evm = " sysfw.itb"
DEPLOY_IMAGES_NAME_append_am65xx-evm = " sysfw-am65x-evm.itb sysfw-am65x_sr2-evm.itb"

ARAGO_TISDK_IMAGE ?= "tisdk-core-bundle"
export IMAGE_BASENAME = "${ARAGO_TISDK_IMAGE}"
