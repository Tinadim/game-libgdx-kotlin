SET DEST=..\..\..\..\core\src
protoc animation.proto --java_out="%DEST%"
protoc ai.proto --java_out="%DEST%"