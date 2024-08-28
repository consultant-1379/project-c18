FROM openkbs/jdk-mvn-py3
WORKDIR /app

COPY Python Python
COPY src src
COPY pom.xml .

RUN sudo chmod a+rwx /app/Python

ENV REPO_CSV "/app/Python/githubRepo.csv"
ENV PYTHON_SCRIPT "/app/Python/githubDataRetrieval.py"
ENV PER_FILE_CSV "/app/Python/githubPerFile.csv"
ENV CONTRIBUTORS_CSV "/app/Python/githubContributors.csv"

RUN pip3 install pydriller
RUN mvn install -DskipTests

CMD ["mvn", "spring-boot:run"]







