# Pull base image.
FROM openjdk:10-jdk-slim

COPY ./ /home/gradle/build/
RUN cd /home/gradle/build/ && rm -rf $(cat .gitignore)

WORKDIR /home/gradle/build/
CMD ["bash"]
