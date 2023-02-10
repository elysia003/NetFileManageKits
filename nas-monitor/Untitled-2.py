from flask import Flask
app = Flask(__name__)

@app.route('/cmd')
def hello_world():
   return 'AAAA'

if __name__ == '__main__':
   app.run('0.0.0.0','22333',True)