import groovy.io.FileType
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

@Grapes([
        @Grab(group = 'io.vertx',module = 'vertx-core',version = '3.8.0'),
        @Grab(group = 'io.vertx',module = 'vertx-web',version = '3.8.0')
])

def basePath = new File('').getAbsolutePath();
def dataPath = basePath + File.separator + 'datas' + File.separator
def httpPort = 8091
if(this.args.size() > 0){
    httpPort = Integer.parseInt(this.args[0])
    if(this.args.size() > 1){
        dataPath = basePath + File.separator + String.valueOf(this.args[1]) + File.separator
    }
}

def vertx = Vertx.vertx()
def router = Router.router()
router.route().handler(BodyHandler.create())

new File(dataPath).eachFileRecurse(FileType.FILES){ file ->
    if(file.name.endsWith(".json")){
        def route = ''
        (file.path - dataPath - file.name).split(File.separator).each { item ->
            route += "/"+item
        }
        def httpReqType = (file.name - '.json').split("_")[0]
        def httpCode = (file.name - '.json').split("_")[1]
        println "$route $httpReqType $httpCode"
        def text = file.getText('UTF-8')
        router.route(getHttpMethod(httpReqType),route).handler{ ctx ->
            ctx.response().setStatusCode(Integer.parseInt(httpCode))
                    .putHeader("content-type", "application/json")
                    .end(text)
        }
    }
}

vertx.createHttpServer().requestHandler(router).listen(httpPort)
println "server started on port $httpPort"
println "datas in path:$dataPath"

def getHttpMethod(String method){
    ["GET" : HttpMethod.GET ,
     "POST" : HttpMethod.POST,
     "PATCH" : HttpMethod.PATCH,
     "PUT" : HttpMethod.PUT,
     "DELETE" : HttpMethod.DELETE,
     "CONNECT" : HttpMethod.CONNECT,
     "HEAD" : HttpMethod.HEAD,
     "OPTIONS" : HttpMethod.OPTIONS,
     "OTHER" : HttpMethod.OTHER,
     "TRACE" : HttpMethod.TRACE][method.toUpperCase()]
}