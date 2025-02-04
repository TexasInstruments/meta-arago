inherit rootfs_ipk
inherit image
inherit image_types
inherit tisdk-sw-manifest

# This defines the list of features that we want to include in the SDK
# image.  The list of packages this will be installed for each features
# is controlled with the FEATURE_PACKAGES_<feature> settings below.
IMAGE_FEATURES ?= ""
IMAGE_FEATURES[type] = "list"

# Always add the sdk_base feature
IMAGE_FEATURES:prepend = "sdk_base package-management "
SDKIMAGE_FEATURES:prepend = "package-management "

# Define our always included sdk package group as the IMAGE_INSTALL settings
# like you would expect.
FEATURE_PACKAGES_sdk_base = "${IMAGE_INSTALL}"

# Create the list of packages to be installed
PACKAGE_INSTALL = "${@' '.join(oe.packagegroup.required_packages('${IMAGE_FEATURES}'.split(), d))}"

RDEPENDS:${PN} += "${@' '.join(oe.packagegroup.active_packages('${IMAGE_FEATURES}'.split(), d))}"

# "export IMAGE_BASENAME" not supported at this time
IMAGE_BASENAME[export] = "1"

# SDK images are unique to a machine
PACKAGE_ARCH = "${MACHINE_ARCH}"

# List of target side images to be built and packaged.  By default take
# the base image but this can be modified if desired to package
# additional images.
TARGET_IMAGES ?= "tisdk-base-image"

# path to install the meta-toolchain package in the SDK
TISDK_TOOLCHAIN_PATH ?= "linux-devkit"

# Linux glibc toolchain recipe(s) to build and package as part of the tisdk bundle
TISDK_TOOLCHAIN ?= "meta-toolchain-arago"
TOOLCHAIN_SUFFIX ?= "-sdk"

# K3R5 baremetal toolchain recipe(s) to build and package as part of the tisdk bundle
TOOLCHAIN_MC_DEP = ""
TOOLCHAIN_MC_DEP:am62lxx = ""
TOOLCHAIN_MC_DEP:k3 = "mc::k3r5:meta-toolchain-arago"
TISDK_TOOLCHAIN_K3R5 ?= "${TOOLCHAIN_MC_DEP}"
TOOLCHAIN_K3R5_SUFFIX ?= "-sdk"

# Since K3R5 packaging happens in the main default MC, these vars
# are not accessible and have to be set here
ARMPKGARCH_K3R5 ?= "armv7a"
TARGET_OS_K3R5 ?= "eabi"

# List of the type of target file system images we want to include
TARGET_IMAGE_TYPES ?= "tar.xz tar.gz ubi wic.gz wic.xz"

# If EXTRA_TISDK_FILES points to a valid directory then all the contents
# of that directory will be added to the SDK using the same directory
# structure found inside of the EXTRA_TISDK_FILES directory.
# This can be set in the recipe or from the command line as long as
# the variable is in the BB_ENV_PASSTHROUGH_ADDITIONS list.
EXTRA_TISDK_FILES ?= ""

# Variable to specify the name of SPL
DEPLOY_SPL_NAME ?= "MLO-${MACHINE}"

# Add generic method of adding prebuilt-images
DEPLOY_IMAGES_NAME ?= ""

# Variable to specify the name of SPL/UART
DEPLOY_SPL_UART_NAME ?= "u-boot-spl.bin-${MACHINE}"

# Variable to specify the name of the TI SCI firmware
DEPLOY_TISCI_FW_NAME ?= ""

# helper function for generating a set of strings based on a list.  Taken
# from the image.bbclass.
def string_set(iterable):
    return ' '.join(set(iterable))

# Add a dependency for the do_rootfs function that will force us to build
# the TARGET_IMAGES first so that they will be available for packaging.
do_rootfs[depends] += "${@string_set('%s:do_image_complete' % pn for pn in (d.getVar("TARGET_IMAGES") or "").split())}"

# Add a dependency for the do_populate_sdk function of the TIDSK_TOOLCHAIN
# variable which will force us to build the toolchain first so that it will be
# available for packaging
do_rootfs[depends] += "${@string_set('%s:do_populate_sdk' % pn for pn in (d.getVar("TISDK_TOOLCHAIN") or "").split())}"
do_rootfs[mcdepends] += "${@string_set('%s:do_populate_sdk' % pn for pn in (d.getVar("TISDK_TOOLCHAIN_K3R5") or "").split())}"

# Call the cleanup_host_packages to remove packages that should be removed from
# the host for various reasons.  This may include licensing issues as well.
OPKG_POSTPROCESS_COMMANDS = "cleanup_host_packages; "

# List of packages to remove from the SDK host package set
HOST_CLEANUP_PACKAGES ?= ""

# Remove any packages that may have been pulled in by other package installs
# that are not desired.  This should be used with caution.
cleanup_host_packages() {
    if [ "${HOST_CLEANUP_PACKAGES}" != "" ]
    then
        opkg ${IPKG_ARGS} --force-depends remove ${HOST_CLEANUP_PACKAGES}
    fi
}

ROOTFS_PREPROCESS_COMMAND += "tisdk_image_setup; "
ROOTFS_POSTPROCESS_COMMAND += "tisdk_image_build; "
IMAGE_PREPROCESS_COMMAND:append = "tisdk_image_cleanup; "

# Create the SDK image.  We will re-use the rootfs_ipk_do_rootfs functionality
# to install a given list of packages using opkg.
fakeroot python do_rootfs () {
    from oe.rootfs import create_rootfs
    from oe.manifest import create_manifest

    # generate the initial manifest
    create_manifest(d)

    # generate rootfs
    create_rootfs(d)
}

tisdk_image_setup () {
    set -x
    rm -rf ${IMAGE_ROOTFS}
    mkdir -p ${IMAGE_ROOTFS}
    mkdir -p ${DEPLOY_DIR_IMAGE}

    mkdir -p ${IMAGE_ROOTFS}/etc
    mkdir -p ${IMAGE_ROOTFS}/var/lib/opkg
    mkdir -p ${IMAGE_ROOTFS}/lib

    if [ -e ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}.sh ]
    then
        chmod 755 ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}.sh
    fi
    if [ -e ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH_K3R5}-${TARGET_OS_K3R5}${TOOLCHAIN_K3R5_SUFFIX}.sh ]
    then
        chmod 755 ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH_K3R5}-${TARGET_OS_K3R5}${TOOLCHAIN_K3R5_SUFFIX}.sh
    fi
}

tisdk_image_build () {
    mkdir -p ${IMAGE_ROOTFS}/filesystem

    # Copy the TARGET_IMAGES to the sdk image before packaging
    for image in ${TARGET_IMAGES}
    do
        for type in ${TARGET_IMAGE_TYPES}
        do
            if [ -e ${DEPLOY_DIR_IMAGE}/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.rootfs.${type} ]
            then
                cp ${DEPLOY_DIR_IMAGE}/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.rootfs.${type} ${IMAGE_ROOTFS}/filesystem/
            fi
        done
    done

    # Copy the pre-built images for kernel and boot loaders
    prebuilt_dir="${IMAGE_ROOTFS}/board-support/prebuilt-images"
    if [ ! -e "${prebuilt_dir}" ]
    then
        mkdir -p ${prebuilt_dir}
    fi

    # Copy the U-Boot image if it exists
    if [ -e ${DEPLOY_DIR_IMAGE}/u-boot.img ]
    then
        cp ${DEPLOY_DIR_IMAGE}/u-boot.img ${prebuilt_dir}/
    elif [ -e ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin ]
    then
        cp ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin ${prebuilt_dir}/
    elif [ -e ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.ais ]
    then
        cp ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.ais ${prebuilt_dir}/
    else
        echo "Could not find the u-boot image"
        return 1
    fi

    # Copy the Kernel image if it exists
    if [ -e ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin ]
    then
        cp ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin ${prebuilt_dir}/
    elif [ -e ${DEPLOY_DIR_IMAGE}/uImage-${MACHINE}.bin ]
    then
        cp ${DEPLOY_DIR_IMAGE}/uImage-${MACHINE}.bin ${prebuilt_dir}/
    elif [ -e ${DEPLOY_DIR_IMAGE}/Image-${MACHINE}.bin ]
    then
        cp ${DEPLOY_DIR_IMAGE}/Image-${MACHINE}.bin ${prebuilt_dir}/
    else
        echo "Could not find the Kernel image"
        return 1
    fi

    # Copy the DTB files if they exist.
    # NOTE: For simplicity remove the uImage- prefix on the dtb files.  Get
    # just the symlink files for a cleaner name.  Use the DTB_FILTER variable
    # to allow finding the dtb files for only that MACHINE type
    # NOTE: The DTB_FILTER variable is interpreted as a regex which means
    #       that for cases where the DTB files to be selected do not have
    #       a common naming you can use something line filter1\|filter2 which
    #       will be interpreted as an "or" and allow matching both expressions.
    #       The \| is important for delimiting these values.
    if [ "${DTB_FILTER}" != "unknown" ]
    then
        for f in `find ${DEPLOY_DIR_IMAGE} -type l -regex ".*\(${DTB_FILTER}\).*\.dtbo?"`
        do
            dtb_file=`basename $f | sed s/.Image-//`
            cp $f ${prebuilt_dir}/${dtb_file}
        done
    fi

    for spl_name in ${DEPLOY_SPL_NAME}
    do
        # Copy the SPL image if it exists
        if [ -e ${DEPLOY_DIR_IMAGE}/$spl_name ]
        then
            cp ${DEPLOY_DIR_IMAGE}/$spl_name ${prebuilt_dir}/
        else
            echo "Could not find the SPL image \"$spl_name\""
            return 1
        fi
    done

    for image_name in ${DEPLOY_IMAGES_NAME}
    do
        if [ -e ${DEPLOY_DIR_IMAGE}/$image_name ]
        then
            cp ${DEPLOY_DIR_IMAGE}/$image_name ${prebuilt_dir}/
        else
            echo "Could not find image \"$image_name\""
            return 1
        fi
    done

    if [ "${DEPLOY_SPL_UART_NAME}" != "" ]
    then
        # Copy the SPL/UART image if it exists
        if [ -e ${DEPLOY_DIR_IMAGE}/${DEPLOY_SPL_UART_NAME} ]
        then
            cp ${DEPLOY_DIR_IMAGE}/${DEPLOY_SPL_UART_NAME} ${prebuilt_dir}/
        fi
    fi

    # Copy TI SCI firmware if it exists
    if [ "${DEPLOY_TISCI_FW_NAME}" != "" ]
    then
        if [ -e ${DEPLOY_DIR_IMAGE}/${DEPLOY_TISCI_FW_NAME} ]
        then
            cp ${DEPLOY_DIR_IMAGE}/${DEPLOY_TISCI_FW_NAME} ${prebuilt_dir}/
        fi
    fi

    # Add the EXTRA_TISDK_FILES contents if they exist
    # Make sure EXTRA_TISDK_FILES is not empty so we don't accidentaly
    # copy the root directory.
    # Use -L to copy the actual contents of symlinks instead of just
    # the links themselves
    if [ "${EXTRA_TISDK_FILES}" != "" ]
    then
        if [ -d "${EXTRA_TISDK_FILES}" ]
        then
            cp -rLf ${EXTRA_TISDK_FILES}/* ${IMAGE_ROOTFS}/
        fi
    fi

    # Copy the licenses directory in the $DEPLOY_DIR to capture all
    # the licenses that were used in the build.
    if [ -d ${DEPLOY_DIR}/licenses ]
    then
        cp -rf ${DEPLOY_DIR}/licenses ${IMAGE_ROOTFS}/
    fi

    # Create the TI software manifest
    if [ ! -d ${IMAGE_ROOTFS}/manifest ]
    then
        mkdir -p ${IMAGE_ROOTFS}/manifest
    fi
    generate_sw_manifest

    # Copy over Linux glibc toolchain sdk installer and give it a simple name which
    # matches the traditional name within the SDK.
    if [ -e ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}.sh ]
    then
        cp ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}.sh ${IMAGE_ROOTFS}/linux-devkit.sh
    fi

    # Copy over K3R5 baremetal toolchain sdk installer and give it a simple name which
    # matches the traditional name within the SDK.
    if [ -e ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH_K3R5}-${TARGET_OS_K3R5}${TOOLCHAIN_K3R5_SUFFIX}.sh ]
    then
        cp ${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH_K3R5}-${TARGET_OS_K3R5}${TOOLCHAIN_K3R5_SUFFIX}.sh ${IMAGE_ROOTFS}/k3r5-devkit.sh
    fi

    # Copy the opkg.conf used by the image to allow for future updates
    cp ${WORKDIR}/opkg.conf ${IMAGE_ROOTFS}/etc/
}

TISDK_IMAGE_CLEANUP_DIRS ?= "var etc lib boot dev home media mnt proc run sbin sys tmp usr"
TISDK_IMAGE_CLEANUP_FILES ?= "bin/bash bin/bash.bash bin/sh"

tisdk_image_cleanup () {
    # Some extra files are now pulled in by the general image class, so remove
    # them.
    for file in ${TISDK_IMAGE_CLEANUP_FILES}
    do
        [ ! -f ${IMAGE_ROOTFS}/$file ] || rm -f ${IMAGE_ROOTFS}/$file
    done

    # Move the var/etc directories which contains the opkg data used for the
    # manifest (and maybe one day for online updates) to a hidden directory.
    for dir in ${TISDK_IMAGE_CLEANUP_DIRS}
    do
        [ ! -d ${IMAGE_ROOTFS}/$dir ] || mv ${IMAGE_ROOTFS}/$dir ${IMAGE_ROOTFS}/.$dir
    done
}

license_create_manifest() {
    :
}
