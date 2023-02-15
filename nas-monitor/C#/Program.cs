using System;
using System.IO;
using LibreHardwareMonitor.Hardware;
using System.Threading;  
namespace ConsoleApp3
{
    internal class Program
    {
        static void show(Computer computer) {
            foreach (IHardware hardware in computer.Hardware)
            {
                hardware.Update();
                
                Console.WriteLine("Hardware: {0}", hardware.Name);

                foreach (IHardware subhardware in hardware.SubHardware)
                {
                    subhardware.Update();
                    Console.WriteLine("\tSubhardware: {0}", subhardware.Name);

                    foreach (ISensor sensor in subhardware.Sensors)
                    {
                        Console.WriteLine("\t\t{0}: {1}, value: {2}", sensor.SensorType, sensor.Name, sensor.Value);
                    }
                }

                foreach (ISensor sensor in hardware.Sensors)
                {
                    Console.WriteLine("\t\t{0}: {1}, value: {2}", sensor.SensorType, sensor.Name, sensor.Value);
                }
            }
        }
        static void setFan(Computer computer,float rate)
        {
            foreach (IHardware hardware in computer.Hardware)
            {
                hardware.Update();

                Console.WriteLine("Hardware: {0}", hardware.Name);

                foreach (IHardware subhardware in hardware.SubHardware)
                {
                    subhardware.Update();
                    Console.WriteLine("\tSubhardware: {0}", subhardware.Name);

                    foreach (ISensor sensor in subhardware.Sensors)
                    {
                        if (sensor.SensorType == SensorType.Control) {
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
        static void Main(string[] args)
        {
            Console.WriteLine("...................................Scan1.........................................");
            Computer computer = new Computer
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
            show(computer);
            //computer.Accept(new UpdateVisitor());
            Console.WriteLine("...................................Report.........................................");
            string result = computer.GetReport();
            StreamWriter sw = File.AppendText("report.txt");
            sw.Write(result);
            sw.Flush();
            sw.Close();
            while (true) {
                Console.WriteLine("Input Fan Rate(%):");
                string s=Console.ReadLine();
                if ("q" == s)
                {
                    break;
                }
                setFan(computer, float.Parse(s));
            }
            computer.Close();
        }
    }
}