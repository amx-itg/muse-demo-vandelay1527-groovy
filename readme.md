# Vandelay_Conf_Rm_1527 V1.0 - Groovy
## Background
This file documents the setup and deployment of the demo conference room "Vandelay Conference Room 1527" for the AMX MUSE Platform, in Groovy.  Please refer to other files for Node-Red, Python, or Javascript implementations.
## MUSE SETUP

### Install Extensions, Jars, and IRL Files
- Upload IRL File - Plug-Ins > idevice
- Upload Duet Files (Vaddio, Display) - Plugins > Duet

### Configure Ports and Devices

#### IR:
- connect IRL to appropriate IR Port

#### Devices:
- Create Devices in System:
    1. Display:
        - Intance ID = dvSamsungQB75R
        - Driver ID = Samsung_QB49N_Comm_dr1_0_0
        - Name = Samsung Display
        - Description = Demo Room Display
        - IP Address = IP address of Display
    2. Vaddio:
        - Instance ID = dvCamera
        - Driver ID = Vaddio_RoboShot12_Comm_dr1_0_0
        - Name = Camera
        - Description = Demo Room Camera

#### HiQnet Compatible Devices:
- MUSE will detect the Hiqnet compatible devices in the same subnet When HiQnet extension is enabled.
    1. BSS Audio BLU-50
        - Device Name Detected - SoundwebLondonBLU-50-2

#### Touch Panel Configuration:
 - In Touch Panel > Settings > Netlinx
 - Change Mode to Master
 - Enter in IP address of Muse Controller
 - Configuration may require Username and Password entered into the touch panel
 

#### Update Demo Code
- Line 19 - Modify "AMX-10003" to match the Hostname of the touch panel connected to the Muse.  
     
### Upload to Muse
- Select the folder "ITG Lab - Groovy" in the Mojo Programming pane and right click
- Select "Upload to Mojo Controller"
- System will prompt to login to controller (Username/Password Required)
- Select the file to be transferred
- Select the Processor(s) as a destination
- NOTE - Hit Enter Twice to automatically send file to the processor