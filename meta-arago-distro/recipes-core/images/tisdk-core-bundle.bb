SUMMARY = "Installer package for TI SDK - NOT for direct use on target"

DESCRIPTION = "This creates an installer including all the default images\
 recommended including source, binaries, filesystems, etc.\
 for TI SDK. This is meant to be used on the host system.\
"

require tisdk-core-bundle.inc

DEPLOY_SPL_NAME:omapl138 = ""
DEPLOY_SPL_NAME:k3 = "tispl.bin tiboot3.bin"

DEPLOY_IMAGES_NAME:k3 = "trusted-firmware-a/bl31.bin optee/bl32.bin Image fitImage fitImage-its-${MACHINE}"
DEPLOY_IMAGES_NAME:append:am62lxx = " trusted-firmware-a/bl1.bin"
DEPLOY_IMAGES_NAME:append:am65xx = " sysfw.itb"
DEPLOY_IMAGES_NAME:append:j721e = " sysfw.itb"
DEPLOY_IMAGES_NAME:append:am65xx-evm = " sysfw-am65x_sr2-gp-evm.itb"
DEPLOY_IMAGES_NAME:append:am65xx-hs-evm = " sysfw-am65x_sr2-hs-evm.itb"
DEPLOY_IMAGES_NAME:append:ti33x = " extlinux.conf"
DEPLOY_IMAGES_NAME:append:ti43x = " extlinux.conf"

ARAGO_TISDK_IMAGE ?= "tisdk-core-bundle"
export IMAGE_BASENAME = "${ARAGO_TISDK_IMAGE}${ARAGO_IMAGE_SUFFIX}"
