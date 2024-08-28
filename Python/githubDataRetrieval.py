from pydriller import Repository
from pydriller.metrics.process.change_set import ChangeSet
from pydriller.metrics.process.code_churn import CodeChurn
from pydriller.metrics.process.contributors_count import ContributorsCount
from pydriller.metrics.process.hunks_count import HunksCount
import csv
import datetime
import pytz
import sys
import os
import timeit




# Retrieve basic data from commits between given dates in repo
def retrieveRepoData(filepath, githubRepository, startDate, endDate):

    numberOfCommits = 0
    numberOfInsertions = 0
    numberOfDeletions = 0

    # Create Dictionary for number of contributions per contributor
    contributorsDict = {}

    # Iterate through commits counting number of commits, lines added and lines deleted between given dates
    for commit in Repository(githubRepository).traverse_commits():
        if commit.committer_date >= startDate and commit.committer_date <= endDate:
            numberOfCommits += 1
            numberOfInsertions += commit.insertions
            numberOfDeletions += commit.deletions

            # Add contribution to contributors tally or add them to dict if they aren't there already
            if commit.committer.name in contributorsDict:
                contributorsDict[commit.committer.name] += 1
            else:
                contributorsDict[commit.committer.name] = 1

    # Extract Change Set numbers
    metric = ChangeSet(githubRepository, startDate, endDate)
    maxChangeSetSize = metric.max()
    avgChangeSetSize = metric.avg()

    # Create Contributors CSV File
    with open(filepath, 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        for contributor in contributorsDict:
            row = [contributor, contributorsDict[contributor]]
            writer.writerow(row)

    f.close()

    # Return row for csv file
    return [numberOfCommits, numberOfInsertions, numberOfDeletions, maxChangeSetSize, avgChangeSetSize]



# Create Repo CSV file with given row
def createRepoCSV(filepath, row):

    # Create a new file if it does not exist
    # Open and write to file
    with open(filepath, 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(row)
    
    # Close file after operations are complete
    f.close()



# Retrieve per file data and create CSV
def retrieveDataAndCreatePerFileCSV(filePath, githubRepository, startDate, endDate):

    # Create list for file names
    fileNames = []

    # Retrieve Code Churn Data and add it to Dictionaries
    metric = CodeChurn(githubRepository, startDate, endDate)
    churnTotalDict = metric.count()
    churnMaxDict = metric.max()
    churnAvgDict = metric.avg()

    # Retrieve Hunks Data and add it to Dictionary
    metric = HunksCount(githubRepository, startDate, endDate)
    hunksCountDict = metric.count()

    # Retrieve Contributors Data and add it to Dictionary
    metric = ContributorsCount(githubRepository, startDate, endDate)
    contributorsCountDict = metric.count()
    minorContributorsCountDict = metric.count_minor()

    # Add file names to fileNames list
    for fileName in churnTotalDict.keys():
        fileNames.append(fileName)

    # Create new file if it does not exist
    # Open and write to file
    with open(filePath, 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        # Extract data for each file
        for fileName in fileNames:
            totalCodeChurn = churnTotalDict[fileName]
            maxCodeChurn = churnMaxDict[fileName]
            avgCodeChurn = churnAvgDict[fileName]
            hunksCount = hunksCountDict[fileName]
            
            # Some files might not have any contributors between given dates
            contributorsCount = 0
            minorContributorsCount = 0
            if fileName in contributorsCountDict:
                contributorsCount = contributorsCountDict[fileName]
            if fileName in minorContributorsCountDict:
                minorContributorsCount = minorContributorsCountDict[fileName]

            # Add row to CSV for each file
            if fileName is None:
                writer.writerow(["Deleted Files", totalCodeChurn, maxCodeChurn, avgCodeChurn, hunksCount, contributorsCount, minorContributorsCount])
            else:
                writer.writerow([fileName, totalCodeChurn, maxCodeChurn, avgCodeChurn, hunksCount, contributorsCount, minorContributorsCount])
    
    # Close file after operations are complete
    f.close()



# Main
if __name__ == "__main__":

    # Timer
    start = timeit.default_timer()

    # Parse command line arguments
    argumentList = sys.argv
    repoUrl = argumentList[1]
    startDateString = argumentList[2]
    endDateString = argumentList[3]
    
    # Parse date strings for datetime formatting
    startYear = int(startDateString[0:4])
    endYear = int(endDateString[0:4])
    startMonth = int(startDateString[4:6])
    endMonth = int(endDateString[4:6])
    startDay = int(startDateString[6:8])
    endDay = int(endDateString[6:8])

    # Convert date Strings to datetime format
    utc = pytz.utc
    startDate = datetime.datetime(startYear, startMonth, startDay, 0, 0, 0, 0, utc)  # year, month, day, hour, minute, second, microsecond, timezone
    endDate = datetime.datetime(endYear, endMonth, endDay, 0, 0, 0, 0, utc) 
    
    # Create file path for CONTRIBUTORS_CSV, this csv is created in 'retrieveRepoData' function
    contributorCSVPath = os.getenv('CONTRIBUTORS_CSV')
    # Create Repo CSV Data row
    rowSegment = retrieveRepoData(contributorCSVPath, repoUrl, startDate, endDate)
    row = [repoUrl, startDateString + "-" + endDateString, rowSegment[0],  rowSegment[1],  rowSegment[2], rowSegment[3], rowSegment[4]]

    # Create Per File CSV Data Row
    perFileCSVPath = os.getenv('PER_FILE_CSV')
    retrieveDataAndCreatePerFileCSV(perFileCSVPath, repoUrl, startDate, endDate)

    # Create Repo CSV
    repoCSVPath = os.getenv('REPO_CSV')
    createRepoCSV(repoCSVPath, row)

    # Timer
    stop = timeit.default_timer()
    print('Time: ', stop - start)  


