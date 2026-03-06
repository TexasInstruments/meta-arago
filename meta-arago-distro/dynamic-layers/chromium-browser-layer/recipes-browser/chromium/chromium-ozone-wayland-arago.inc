PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/chromium-ozone-wayland-142.0.7444.175:"

PACKAGECONFIG:append = " proprietary-codecs"

# Remove incompatible Rust 1.8x patch (for Rust 1.93+)
SRC_URI:remove = "file://chromium-142-crabbyavif-rust18x.patch"

SRC_URI:append = " \
                  file://0001-media-gpu-v4l2-Use-ChromeOS-style-dev-paths-for-all-.patch \
                  file://0002-chromium-gpu-sandbox-Allow-GPU-sandbox-access-to-V4L.patch \
                  file://0003-chromium-gpu-v4l2-Fix-OUTPUT-queue-streaming-in-V4L2.patch \
                  file://0004-media-gpu-v4l2-Avoid-placing-incomplete-H264-access-.patch \
                  "

# Disable ThinLTO to prevent linker OOM crashes
GN_ARGS += " use_thin_lto=false"

# Override do_copy_clang_library to handle both target and host architectures
do_copy_clang_library() {
    cp -r "${STAGING_LIBDIR_NATIVE}/clang/latest" "${STAGING_DIR_HOST}${nonarch_libdir}/clang/"
    cd "${STAGING_DIR_HOST}${nonarch_libdir}/clang" || return

    # Process both target arch (aarch64) and build arch (x86_64)
    for ARCH in "${TARGET_ARCH}" "${BUILD_ARCH}"; do
        echo "Processing clang libraries for architecture: $ARCH"
        target_dir="latest/lib/${ARCH}-unknown-linux-gnu"
        mkdir -p "$target_dir"

        # Determine source sysroot based on architecture
        if [ "$ARCH" = "${TARGET_ARCH}" ]; then
            search_path="${STAGING_LIBDIR}"
        else
            search_path="${STAGING_LIBDIR_NATIVE}"
        fi

        # Find and copy library files from the appropriate sysroot
        for lib_pattern in "libclang_rt.builtins-${ARCH}.a" "liborc_rt-${ARCH}.a"; do
            lib_files=$(find "$search_path/clang" -name "$lib_pattern" 2>/dev/null)
            for lib_file in $lib_files; do
                if [ -f "$lib_file" ]; then
                    base_name=$(basename "$lib_file")
                    echo "Copying $lib_file to $target_dir/"
                    cp "$lib_file" "$target_dir/" 2>/dev/null || true
                fi
            done
        done

        # Create generic names without architecture suffix
        if [ -d "$target_dir" ]; then
            cd "$target_dir" || continue
            for file in libclang_rt.builtins-*.a liborc_rt-*.a; do
                if [ -f "$file" ]; then
                    generic_name=$(echo "$file" | sed -E 's/-(x86_64|aarch64|arm|i686)\.a$/.a/')
                    if [ "$file" != "$generic_name" ] && [ ! -f "$generic_name" ]; then
                        cp "$file" "$generic_name" 2>/dev/null || true
                    fi
                fi
            done
            cd "${STAGING_DIR_HOST}${nonarch_libdir}/clang" || return
        fi

        # Copy to native sysroot as well
        native_arch_path="${STAGING_LIBDIR_NATIVE}/clang/latest/lib/${ARCH}-unknown-linux-gnu/"
        if [ -d "$target_dir" ] && [ -n "$(ls -A "$target_dir" 2>/dev/null)" ]; then
            mkdir -p "$native_arch_path"
            cp -n "$target_dir"/* "$native_arch_path/" 2>/dev/null || true
        fi
    done
}

# Add pkg-config setup for NSS detection
do_configure:prepend() {
    export PKG_CONFIG_PATH="${STAGING_LIBDIR}/pkgconfig:${STAGING_DATADIR}/pkgconfig"
    export PKG_CONFIG_SYSROOT_DIR="${STAGING_DIR_TARGET}"
    export PKG_CONFIG_LIBDIR="${STAGING_LIBDIR}/pkgconfig"
}
