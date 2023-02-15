  import os
  import clr #pythonnet
  
  
  def initialize():
  
      clr.AddReference(os.getcwd() + "\\LibreHardwareMonitorLib.dll")
  
      from LibreHardwareMonitor import Hardware
  
      handle = Hardware.Computer()
      handle.IsCpuEnabled = True
      handle.IsGpuEnabled = True
      handle.IsMemoryEnabled = True
      handle.IsMotherboardEnabled = True
      handle.IsControllerEnabled = True
      handle.IsNetworkEnabled = True
      handle.IsStorageEnabled = True
      handle.Open()
      return handle
  
  
  def fetch_stats(handle):
      for i in handle.Hardware:
          i.Update()
          for sensor in i.Sensors:
              parse_sensor(sensor)
  
  
  def parse_sensor(sensor):
      if sensor.Value is not None:
          data = (f"{sensor.Hardware.Name} "
                       f"{sensor.Name} "
                       f"{sensor.Value}")
          print(data)


  if __name__ == "__main__":
      HardwareHandle = initialize()
      fetch_stats(HardwareHandle)