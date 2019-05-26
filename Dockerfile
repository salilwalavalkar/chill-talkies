# There are now 3 layers, with all the application resources in the
# later 2 layers. If the application dependencies don’t change, then the first layer
# (from BOOT-INF/lib) will not change, so the build will be faster, and so will the
# startup of the container at runtime as long as the base layers are already cached.

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.chill.talkies.Application"]