import win32gui
import sys
from PyQt5.QtWidgets import QApplication,QWidget
from pySMART import Device
import wmi
import json as JSON
from pySMART import Device
import clr
import time
def qt():
    app = QApplication(sys.argv) 
    w = QWidget()  
    w.resize(400,200)  
    w.move(300,300) 
    w.setWindowTitle("")  
    m_hTaskbar = win32gui.FindWindow("Shell_TrayWnd", None)
    m_hBar = win32gui.FindWindowEx(m_hTaskbar, 0, "ReBarWindow32", None)
    m_hMin = win32gui.FindWindowEx(m_hBar, 0, "MSTaskSwWClass", None)
    b = win32gui.GetWindowRect(m_hBar)  # m_hBar
    win32gui.MoveWindow(m_hMin, 0, 0, b[2] - b[0] - 200, b[3] - b[1], True)
    w.setGeometry(0, 0, 1000, b[3] - b[1])
    win32gui.SetParent(int(w.winId()), m_hTaskbar)  # 任务栏为父窗口
    w.show() 
    sys.exit(app.exec_()) 
#   
conn = wmi.WMI()
def getMem():
    memory = conn.Win32_OperatingSystem()[0]
    total = int(memory.TotalVisibleMemorySize) / 1024  
    free = int(memory.FreePhysicalMemory) / 1024
    used = total - free
    print(f'内存使用情况: 共{total:.2f} MB, 已使用{used:.2f} MB, 空闲{free:.2f} MB')
def getCpu():
    #  CPU 使用率
    cpu_usage = conn.Win32_PerfFormattedData_PerfOS_Processor()[0].PercentProcessorTime
    print(f'CPU使用率: {cpu_usage}%')
def getNetwork():
    # 网络流量信息
    network = conn.Win32_PerfFormattedData_Tcpip_NetworkInterface()
    for n in network:
        print(f'网卡 {n.Name} 的下载速度: {(str)(n.BytesReceivedPerSec)} KB/s')
        print(f'网卡 {n.Name} 的上传速度: {(str)(n.BytesSentPerSec)} KB/s')
def getPartition():
    # 分区信息
    GB = 1024 ** 3
    Data = []
    for Physical_Disk in conn.Win32_DiskDrive():
        for Partition in Physical_Disk.associators("Win32_DiskDriveToDiskPartition"):
            for Logical_Disk in Partition.associators("Win32_LogicalDiskToPartition"):
                TmpDict = {}
                TmpDict["盘符"] = Logical_Disk.Caption
                TmpDict["总量"] = format(int(Logical_Disk.Size) / GB, '.2f')
                TmpDict["使用量"] = format((int(Logical_Disk.Size) - int(Logical_Disk.FreeSpace)) / GB, '.2f')
                TmpDict["空闲量"] = format(int(Logical_Disk.FreeSpace) / GB, '.2f')
                TmpDict["使用率"] = format(int(100.0 * (int(Logical_Disk.Size) - int(Logical_Disk.FreeSpace)) / int(Logical_Disk.Size)), '.2f') + "％"
                Data.append(TmpDict)
    print(Data)
def getDisk():
    # 磁盘使用情况
    disk = conn.Win32_LogicalDisk()  # DriveType=3 表示硬盘
    for d in disk:
        total = int(d.Size) / 1024 / 1024 / 1024 
        free = int(d.FreeSpace) / 1024 / 1024 / 1024
        used = total - free
        print(f'磁盘 {d.Caption} 使用情况: 共{total:.2f} GB, 已使用{used:.2f} GB, 空闲{free:.2f} GB')
def getSmart():
    sda = Device('/dev/sda')
    print('磁盘 /dev/sda '+str(sda.temperature)+' °C')
    sdb = Device('/dev/sdb')
    print('磁盘 /dev/sdb '+str(sdb.temperature)+' °C')
clr.AddReference("C:/Users/Kallen/Desktop/gui/OpenHardwareMonitor/OpenHardwareMonitorLib.dll")
from OpenHardwareMonitor.Hardware import Computer 
computer_tmp = Computer()
computer_tmp.CPUEnabled = True #CPU温度
computer_tmp.GPUEnabled = True #GPU温度
computer_tmp.HDDEnabled = True #HDD温度
computer_tmp.RAMEnabled = True #RAM温度
computer_tmp.MEMEnabled = True #RAM温度
computer_tmp.Open()
print (computer_tmp.Hardware[0].Identifier)  # 0 CPU 
print (computer_tmp.Hardware[1].Identifier)  # 1 Ram 
print (computer_tmp.Hardware[2].Identifier)  # 2 GPU
print (computer_tmp.Hardware[3].Identifier)  # 3 HDD
print (computer_tmp.Hardware[4].Identifier)  # 3 HDD
print (len(computer_tmp.Hardware[0].Sensors))  # 25
print (len(computer_tmp.Hardware[1].Sensors))  # 3
print (len(computer_tmp.Hardware[2].Sensors))  # 17
print (len(computer_tmp.Hardware[3].Sensors))  # 6
print (len(computer_tmp.Hardware[4].Sensors))  # 6
def getOpenHardwareMonitor():
    computer_tmp.Hardware[0].Update()
    computer_tmp.Hardware[1].Update()
    computer_tmp.Hardware[2].Update()
    computer_tmp.Hardware[3].Update()
    for a in range(0, len(computer_tmp.Hardware[0].Sensors)):
        print (computer_tmp.Hardware[0].Sensors[a].Identifier ,computer_tmp.Hardware[0].Sensors[a].Value)      
    for b in range(0, len(computer_tmp.Hardware[1].Sensors)):
        print (computer_tmp.Hardware[1].Sensors[b].Identifier ,computer_tmp.Hardware[1].Sensors[b].Value)      
    for c in range(0, len(computer_tmp.Hardware[2].Sensors)):
        print (computer_tmp.Hardware[2].Sensors[c].Identifier ,computer_tmp.Hardware[2].Sensors[c].Value)      
    for d in range(0, len(computer_tmp.Hardware[3].Sensors)):
        print (computer_tmp.Hardware[3].Sensors[d].Identifier ,computer_tmp.Hardware[3].Sensors[d].Value)
    for d in range(0, len(computer_tmp.Hardware[4].Sensors)):
        print (computer_tmp.Hardware[3].Sensors[d].Identifier ,computer_tmp.Hardware[4].Sensors[d].Value)

# while True:
    # pass
    #getMem()
    #getCpu()
    #getNetwork()
    #getPartition()
    #getDisk()
    #getSmart()
    # getOpenHardwareMonitor()
    # time.sleep(5)

  
