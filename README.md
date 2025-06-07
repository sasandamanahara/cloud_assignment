# Cloud Computing Assignment - EC7205 
**Index Number:** EG/2020/4068 | EG/2020/4207 | EG/2020/4086

## 📁 Dataset Details

- **File Name:** `Amazon-Products.csv`
- **Size:** ~180MB
- **Link:** 
- **Source:** Public product metadata
- **Format:** CSV
- **Relevant Fields Used:**  
  - `rating` (float)  
  - `category` (string)

### Technologies Used
- Hadoop 3.2.1 (via [BDE Docker images](https://github.com/big-data-europe/docker-hadoop))
- Java 1.8 (OpenJDK)
- MapReduce (Java-based)
- Ubuntu VPS (Hostinger VPS) with Docker
- HDFS

### Container Setup
Multiple Hadoop containers were launched via Docker (namenode, datanode, resourcemanager, nodemanager).

### Step 1: Upload CSV to VPS
## From local machine:
scp Amazon-Products.csv root@<vps-ip>:/root/

### Step 2: Copy CSV to Hadoop namenode container
docker cp Amazon-Products.csv namenode:/root/

### Step 3: Add file to HDFS
docker exec -it namenode /bin/bash
hdfs dfs -mkdir -p /input
hdfs dfs -put /root/Amazon-Products.csv /input

### Step 4: Compile and Package MapReduce Java Code
cd /root/RatingAverage
javac -classpath `hadoop classpath` -d . RatingMapper.java RatingReducer.java RatingDriver.java
jar -cvf rating-average.jar -C RatingAverage/ .

### Step 5: Run the MapReduce Job
hadoop jar rating-average.jar RatingDriver /input/Amazon-Products.csv /output/average_rating_java

### Step 6: Export Results
hdfs dfs -cat /output/average_rating_java/part-r-00000 > /root/avg_ratings.txt

### Download the result file to your local PC:
scp root@<vps-ip>:/root/avg_ratings.txt .

