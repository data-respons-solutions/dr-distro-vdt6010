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
$ MACHINE=vdt6010-factory bitbake factory-image
```

### Datarespons reference distro
`$ bitbake vdt6010-image`

### Datarespons reference distro SDK
`$ bitbake vdt6010-image -c populate_sdk`

## Usage

### Console

Serial console through debug uart, baud 115200.

or

ssh by certificate. Reference distro certificate in meta-datarespons/recipes-security/ssh-keys/droot

`$ ssh -i droot root@ip`

### Applications
**swap-root**

Update rootfs
 
**image-install**

Fresh install from USB

**backlightctl**

Control backlight by motion sensor

Example:

```
# Expects motion sensor connected to gpio138
# Enable gpio interrupt
echo rising > /sys/class/gpio/gpio138/edge
backlightctl -t 30 -i /sys/class/gpio/gpio138 /sys/class/backlight/backlight-lvds/
```

**flash-uboot**

Write uboot to spi nor flash

Example:

`$ flash-uboot --flash mtd --spl SPL --spl-offset 0x400 --uboot u-boot-ivt.img --write`

**chromium**

Web browser

Example fullscreen:

`$ chromium --no-sandbox --kiosk --no-first-run --incognito www.datarespons.com`

**nmcli**

Network configuration

Example set static IP:

```
$ nmcli con mod "Wired connection 1" ipv4.address 192.168.1.170/24
$ nmcli con mod "Wired connection 1" ipv4.gateway 192.168.1.1
$ nmcli con mod "Wired connection 1" ipv4.dns 8.8.8.8
$ nmcli con mod "Wired connection 1" ipv4.method manual
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
