SUMMARY = "Installer package for TI SDK - NOT for direct use on target"

DESCRIPTION = "This creates an installer including all the default images\
 recommended including source, binaries, filesystems, etc.\
 for TI SDK. This is meant to be used on the host system.\
"

require tisdk-core-bundle.inc

DEPLOY_SPL_NAME:omapl138 = ""
DEPLOY_SPL_NAME:k3 = "tispl.bin tiboot3.bin"

DEPLOY_IMAGES_NAME:k3 = "bl31.bin bl32.bin bl31.bin.unsigned bl32.bin.unsigned"
DEPLOY_IMAGES_NAME:append:am65xx = " sysfw.itb"
DEPLOY_IMAGES_NAME:append:j721e = " sysfw.itb"
DEPLOY_IMAGES_NAME:append:am65xx-evm = " sysfw-am65x-gp-evm.itb"
DEPLOY_IMAGES_NAME:append:am65xx-hs-evm = " sysfw-am65x_sr2-hs-evm.itb"

ARAGO_TISDK_IMAGE ?= "tisdk-core-bundle"
export IMAGE_BASENAME = "${ARAGO_TISDK_IMAGE}"
