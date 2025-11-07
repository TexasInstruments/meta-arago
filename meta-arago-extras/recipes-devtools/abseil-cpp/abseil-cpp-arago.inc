FILESEXTRAPATHS:prepend := "${THISDIR}/abseil-cpp:"

PV = "20240722.0"

SRCREV = "4447c7562e3bc702ade25105912dce503f0c4010"
BRANCH = "lts_2024_07_22"

SRC_URI:remove = "\
    file://abseil-ppc-fixes.patch \
    file://0004-PR-1644-unscaledcycleclock-remove-RISC-V-support.patch \
    file://0001-PR-1739-container-internal-Explicitly-include-cstdin.patch \
"

SRC_URI += "\
    file://0004-abseil-ppc-fixes.patch \
    file://0005-Don-t-match-Wnon-virtual-dtor-in-the-flags-are-neede.patch \
"
