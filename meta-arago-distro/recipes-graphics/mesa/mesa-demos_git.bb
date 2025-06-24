SUMMARY = "Mesa demo applications"
DESCRIPTION = "This package includes the demonstration application, such as glxgears. \
These applications can be used for Mesa validation and benchmarking."
HOMEPAGE = "http://mesa3d.org"
BUGTRACKER = "https://bugs.freedesktop.org"

LICENSE = "MIT & PD"
LIC_FILES_CHKSUM = "file://src/xdemos/glxgears.c;beginline=1;endline=20;md5=a481fbdb2ac800ecb12405c33bcfacae \
                    file://src/xdemos/glxdemo.c;beginline=1;endline=8;md5=b01d5ab1aee94d35b7efaa2ef48e1a06"

SRC_URI = "git://gitlab.freedesktop.org/mesa/demos.git;protocol=https;branch=main"
SRCREV = "02eae079cb0b8186064d5b194ca4cc2995f099ee"

DEFAULT_PREFERENCE = "-1"
PV = "9.0.0+git"

inherit meson pkgconfig features_check
REQUIRED_DISTRO_FEATURES = "opengl"

EXTRA_OEMESON = "-Dwith-system-data-files=true"

PACKAGECONFIG ?= "drm egl gles1 gles2 \
                  ${@bb.utils.filter('DISTRO_FEATURES', 'vulkan x11 wayland', d)}"

PACKAGECONFIG[drm] = "-Dlibdrm=enabled,-Dlibdrm=disabled,libdrm"
PACKAGECONFIG[egl] = "-Degl=enabled,-Degl=disabled,virtual/egl"
PACKAGECONFIG[gles1] = "-Dgles1=enabled,-Dgles1=disabled,virtual/libgles1"
PACKAGECONFIG[gles2] = "-Dgles2=enabled,-Dgles2=disabled,virtual/libgles2"
PACKAGECONFIG[glut] = "-Dwith-glut=${STAGING_EXECPREFIXDIR},-Dglut=disabled,freeglut"
PACKAGECONFIG[osmesa] = "-Dosmesa=enabled,-Dosmesa=disabled,"
PACKAGECONFIG[vulkan] = "-Dvulkan=enabled,-Dvulkan=disabled,vulkan-loader glslang-native"
PACKAGECONFIG[wayland] = "-Dwayland=enabled,-Dwayland=disabled,virtual/libgl wayland wayland-native wayland-protocols libxkbcommon libdecor"
PACKAGECONFIG[x11] = "-Dx11=enabled,-Dx11=disabled,virtual/libx11 libglu libxkbcommon libxcb"

do_install:append() {
	# it can be completely empty when all PACKAGECONFIG options are disabled
	rmdir --ignore-fail-on-non-empty ${D}${bindir}

	if [ -f ${D}${bindir}/clear ]; then
		mv ${D}${bindir}/clear ${D}${bindir}/clear.mesa-demos
	fi
}
