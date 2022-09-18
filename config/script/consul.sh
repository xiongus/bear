docker run \
    -d \
    --net=app \
    --restart=always \
    -e 'CONSUL_LOCAL_CONFIG={"leave_on_terminate": true}' \
    -p 8500:8500 \
    -p 8600:8600/udp \
    --name=consul \
    consul agent -server -ui -node=server -bootstrap-expect=1 -client=0.0.0.0