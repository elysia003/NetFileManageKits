using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace NasMonitor
{
    public class HttpServer
    {
        private HttpListener _listener;
        public static int _port;

        public HttpServer(int port)
        {
            _port = port;
        }

        public void Start()
        {
            _listener = new HttpListener();
            _listener.Prefixes.Add(String.Format("http://*:{0}/", _port.ToString()));
            _listener.Start();
            _listener.BeginGetContext(ProcessRequest, null);
        }

        public void Stop()
        {
            _listener.Stop();
        }

        private void ProcessRequest(IAsyncResult result)
        {
            HttpListenerContext context = null;
            try
            {
                _listener.BeginGetContext(ProcessRequest, null);
                context = _listener.EndGetContext(result);

                // 发送短信内容
                Console.WriteLine(context.Request.Url.AbsolutePath);
                // Http回复
                string resp = "empty";
                switch (context.Request.Url.AbsolutePath)
                {
                    case "/":
                        resp = Program.monitor.getJson();
                        break;
                    case "/setFanRate":
                        string id=context.Request.QueryString.Get("id");
                        string rate=context.Request.QueryString.Get("rate");
                        resp= Program.monitor.setFanRate(id,float.Parse(rate));
                        break;
                    default:
                        break;
                }
                using (StreamWriter writer = new StreamWriter(context.Response.OutputStream,Encoding.UTF8))
                {
                    context.Response.StatusCode = 200;
                    byte[] buffer = Encoding.UTF8.GetBytes(resp);
                    context.Response.AddHeader("ContentType", "application/json;charset=utf-8");
                    writer.Write(resp);
                    writer.Close();
                }
                context.Response.StatusCode = 200;
                context.Response.Close();
            }
            catch (Exception e)
            {
                using (StreamWriter writer = new StreamWriter(context.Response.OutputStream))
                {
                    context.Response.StatusCode = 400;
                    writer.Write(e.Message);
                    writer.Close();
                }
            }
        }
    }
}
