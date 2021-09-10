import random
import sys
if(len(sys.argv) > 1 and  sys.argv[1].isdigit()):
    i = 0
    text_file = open("output.txt", "w")
    while(i<int(sys.argv[1])):
        #Details Format
        #cardNo,dd/mm/yyyy/,mm/yyyy,lostOrStolenStatus,pin,blockedStatus
        current_random = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
        current_pin = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
        joined_pin = "".join(current_pin)
        joined_string = "".join(current_random)
        random_month = str(random.randint(1,12))
        if(random_month == 2):
            random_day = str(random.randint(1,28))
        elif(random_month == 4 or random_month == 6 or random_month == 9 or random_month == 11):
            random_day = str(random.randint(1,30))
        else:
            random_day = str(random.randint(1,31))
        random_issue_year = str(random.randint(2017,2019))
        random_expiry_year = str(random.randint(2020,2022))
        issue_string = random_day + "/" + random_month
        issue_string = issue_string + "/" + random_issue_year
        expiry_string = random_month + "/" + random_expiry_year
        lost_stolen_status = str(random.randint(0,1))
        blocked_status = str(0)
        joined_string += "," + issue_string
        joined_string += "," + expiry_string
        joined_string += "," + lost_stolen_status
        joined_string += "," + joined_pin + "," + "0"
        joined_string = joined_string + "\n"
        text_file.write(joined_string)
        i += 1
    text_file.close()
else:
    print("Enter Correct Args - Num of Cards")