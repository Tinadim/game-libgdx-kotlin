SET DEST=..\..\..\..\core\src
protoc animation.proto --java_out="%DEST%"
protoc ai.proto --java_out="%DEST%"
protoc entity.proto --java_out="%DEST%"
protoc screen.proto --java_out="%DEST%"