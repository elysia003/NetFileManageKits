using System;
using System.IO;

using System.Threading;  
namespace NasMonitor
{
    internal class Program
    {
        public static Monitor monitor;
        static void Main(string[] args)
        {
            monitor = new Monitor();
            HttpServer httpServer = new HttpServer(16130);
            httpServer.Start();
            Console.WriteLine("...................................Scan1.........................................");
            //computer.Accept(new UpdateVisitor());
            Console.WriteLine("...................................Report.........................................");
            string result = monitor.getReport();
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
                monitor.setFan(float.Parse(s));
            }
        }
    }
}