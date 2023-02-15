using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace ConsoleApp3
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
                var reader = new System.IO.StreamReader(context.Request.InputStream);
                String data = reader.ReadToEnd();

                // Http回复
                using (StreamWriter writer = new StreamWriter(context.Response.OutputStream))
                {
                    context.Response.StatusCode = 200;
                    writer.Write(data);
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
