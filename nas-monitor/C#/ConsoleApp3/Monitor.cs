using System;
using LibreHardwareMonitor.Hardware;
namespace NasMonitor
{
    internal class Monitor
    {
        private Computer computer;

        public string getJson() {
            string json = "[";
            int i = 0;
            foreach (IHardware hardware in computer.Hardware)
            {
                hardware.Update();
                json += string.Format("{{\"HardwareName\": \"{0}\",\"HardwareType\":\"{1}\",\"id\":\"{2}\",", hardware.Name,hardware.HardwareType,""+i);
                json += "\"sensor\":[";
                int j = 0;
                foreach (ISensor sensor in hardware.Sensors)
                {
                    json += string.Format("{{\"SensorType\":\"{0}\",\"SensorName\":\"{1}\",\"Value\":\"{2}\",\"id\":\"{3}\"}},", sensor.SensorType, sensor.Name, sensor.Value,""+i+"_"+j);
                    j++;
                }
                json += "],\"Subhardware\":[";
                int k = 0;
                foreach (IHardware subhardware in hardware.SubHardware)
                {
                    subhardware.Update();
                    json += string.Format("{{\"HardwareName\": \"{0}\",\"HardwareType\":\"{1}\",\"id\":\"{2}\",", hardware.Name, hardware.HardwareType,""+i+"_"+k);
                    json += "\"sensor\":[";
                    j = 0;
                    foreach (ISensor sensor in subhardware.Sensors)
                    {
                        json += string.Format("{{\"SensorType\":\"{0\",\"SensorName\":\"{1}\",\"Value\":\"{2}\",\"id\":\"{3}\"}},", sensor.SensorType, sensor.Name, sensor.Value, "" + i + "_" + k+"_"+j);
                        j++;
                    }
                    k++;
                }
                json += "]";
                json += "},";
                i++;
            }
           
            json += "]";
            json=json.Replace("},]","}]");
            //Console.WriteLine(json);
            return json;
        }
        public string show()
        {
            string res ="";
            foreach (IHardware hardware in computer.Hardware)
            {
                hardware.Update();
                res += string.Format("Hardware: {0}\n", hardware.Name);
                foreach (ISensor sensor in hardware.Sensors)
                {
                    res += string.Format("\t{0}: {1}, value: {2}\n", sensor.SensorType, sensor.Name, sensor.Value);
                }
                foreach (IHardware subhardware in hardware.SubHardware)
                {
                    subhardware.Update();
                    res += string.Format("\tSubhardware: {0}\n", subhardware.Name);

                    foreach (ISensor sensor in subhardware.Sensors)
                    {
                        res += string.Format("\t\t{0}: {1}, value: {2}\n", sensor.SensorType, sensor.Name, sensor.Value);
                    }
                }


            }
            return res;
        }
        public void setFan(float rate)
        {
            foreach (IHardware hardware in this.computer.Hardware)
            {
                hardware.Update();

                Console.WriteLine("Hardware: {0}", hardware.Name);

                foreach (IHardware subhardware in hardware.SubHardware)
                {
                    subhardware.Update();
                    Console.WriteLine("\tSubhardware: {0}", subhardware.Name);

                    foreach (ISensor sensor in subhardware.Sensors)
                    {
                        if (sensor.SensorType == SensorType.Control)
                        {
                            sensor.Control.SetSoftware(rate);
                        }
                        Console.WriteLine("\t\t{0}: {1}, value: {2}", sensor.SensorType, sensor.Name, sensor.Value);
                    }
                }

                foreach (ISensor sensor in hardware.Sensors)
                {
                    if (sensor.SensorType == SensorType.Control)
                    {
                        sensor.Control.SetSoftware(rate);
                    }
                    Console.WriteLine("\t\t{0}: {1}, value: {2}", sensor.SensorType, sensor.Name, sensor.Value);
                }
            }
        }

        public string setFanRate(string id,float rate) {
            try
            {
                string[] ids = id.Split('_');
                if (ids.Length == 2)
                {
                    computer.Hardware[int.Parse(ids[0])].Sensors[int.Parse(ids[1])].Control.SetSoftware(rate);
                }
                if (ids.Length == 3)
                {
                    computer.Hardware[int.Parse(ids[0])].SubHardware[int.Parse(ids[1])].Sensors[int.Parse(ids[2])].Control.SetSoftware(rate);
                }
                return "succ!";
            }
            catch(Exception e){
                return e.Message;
            }
        }

        public string getReport() {
            return computer.GetReport();
        }

        public Monitor() {
            computer = new Computer
            {
                IsCpuEnabled = true,
                IsGpuEnabled = true,
                IsMemoryEnabled = true,
                IsMotherboardEnabled = true,
                IsControllerEnabled = true,
                IsNetworkEnabled = true,
                IsStorageEnabled = true,
                IsPsuEnabled = true,
                //IsBatteryEnabled=true
            };
            computer.Open();
            show();
        }
        ~Monitor() {
            computer.Close();
        }
    }
}