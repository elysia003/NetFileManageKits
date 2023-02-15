import win32gui
import sys
from PyQt5.QtWidgets import QApplication,QWidget
from pySMART import Device
import wmi
import json as JSON
from pySMART import Device
import clr
import time
def initialize():  
    clr.AddReference("C:/Users/Kallen/Desktop/新建文件夹 (3)/NetFileManageKits/nas-monitor/OpenHardwareMonitor/OpenHardwareMonitorLib.dll")
    from OpenHardwareMonitor.Hardware import Computer
    handle = Computer()
    handle.MainboardEnabled = True
    handle.CPUEnabled = True #CPU温度
    handle.GPUEnabled = True #GPU温度
    handle.HDDEnabled = True #HDD温度
    handle.RAMEnabled = True #RAM温度
    handle.SuperIOEnabled = True #RAM温度
    handle.GpuAtiEnabled = True #RAM温度
    handle.TBalancerEnabled = True #RAM温度
    handle.HeatmasterEnabled = True #RAM温度
    handle.Controller=True
    handle.Open()
    return handle
def fetch_stats(handle):
    for i in handle.Hardware:
        i.Update()
        print(i.SubHardware)
        i.Update()
        print(i.SubHardware)
        for sensor in i.Sensors:
            parse_sensor(sensor)
def parse_sensor(sensor):
    idata = (f"{sensor.Hardware.Name} "
                       f"{sensor.Name} "
                       f"{sensor.Value}")
    print(idata)
if __name__ == "__main__":
    HardwareHandle = initialize()
    fetch_stats(HardwareHandle)