VDT6010 SDK
===========

## Maintainer
Mikko Salomäki <ms@datarespons.se>

Build
-----
External recipe sources are included in the project as git submodules.
These modules need to be initialized on first use:

Start of by initializing git

`$ git submodule update --init`

OpenEmbedded (BitBake) relies on the build environment providing a large
set of environment variables.  These are most easily set up by sourcing
the provide 'env' script:

`$ . ./env`

This will generate artifacts under the build sub-folder.
If you need more flexibility in where to build do:

```
$ export TEMPLATECONF=<top of project>/build/conf
$ export DR_CM_COMMIT=`git -C <top of project> describe --tags --long --dirty`
$ export DR_BUILD_PLAN=<name of your build>
$ export DR_BUILD_NO=<yout build number if needed>
$ export BB_ENV_EXTRAWHITE="DR_BUILD_PLAN DR_BUILD_NO DR_CM_COMMIT"
$ source <top of project>/oe-core/oe-init-build-env build <top of project>/bitbake/
```

### Factory
Needed for flashing u-boot through SDP (serial download protocol).

Generate factory image , factory tools and u-boot(factory and production) binaries:

```
$ bitbake factory-tools
$ MACHINE=vdt6010-factory bitbake factory-install
$ MACHINE=vdt6010-factory bitbake factory-image
$ MACHINE=vdt6010-factory bitbake u-boot
```

### Datarespons reference distro
`$ bitbake datarespons-image`

### Datarespons reference distro SDK
`$ bitbake datarespons-image -c populate_sdk`


Usage
-----
### Flash u-boot
* Build tools and binaries: [Factory](#Factory)
* Install factory tools:

`$ <top of project>/build/tmp-glibc/deploy/sdk/vdt6010-factory-tools-*.sh`
* Source tools environment:

`$ . <tools install dir>/environment-setup-*`
* Set system into [Rescue mode](#Rescue%20mode).
* Move to image directory and run build script:

```
$ cd <top of project>/build/tmp-glibc/deploy/images/vdt6010-factory
$ ./factory-install.sh <spl> <uboot>
# Note: factory-install must be run as superuser. If sudo is used then
#       we must make sure to preserve factory tools build environment.
        e.g: $ sudo env "PATH=$PATH" ./factory-install.sh <spl> <uboot>
```

### Flash u-boot bootsplash
Supports gzipped or raw bmp. Max file size 2MB.

Install by:

`$ dd if=<BMP> of=/dev/$(awk 'BEGIN { FS = ":" } ; /"splash"/{ print $1 }' /proc/mtd)`

### Rescue mode
Forcing system into rescue mode allows re-flashing u-boot externally with factory image.

Access following behind service hatch:
* SW 1 (see table below)
* USB_OTG to PC

|Mode  |SW1-1|SW1-2|SW1-3|SW1-4|
|------|-----|-----|-----|-----|
|Rescue|ON   |OFF  |OFF  |ON   |
|Normal|OFF  |ON   |ON   |OFF  |

Open items
----------
### Linux:
* bootsplash

### Possible coming hardware changes
* Allow USB hub reset from SW
	* Mount R336, Demount R335, C217
