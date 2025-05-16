SUMMARY = "Standalone toolchain/devkit for TI-SDK products"

TOOLCHAIN_SUFFIX ?= "-tisdk"
LICENSE = "MIT"

require meta-toolchain-arago-tisdk.inc
require recipes-core/meta/meta-toolchain-arago.bb

PR = "${INC_PR}.0"
