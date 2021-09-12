import random
import sys

##Run With Sys Args:
#Arg 1: Total number of desired cards to be generated
#Arg 2 (Optional): Card Lost/Stolen Bias Mode
#                   Limit total lost/stolen to < 10%
#                   Enable by setting arg to "1"

def lostOrStolenStatusBias(total):
    #Bias = 10% Max
    total_left = abs(total/10)
    output = []
    for i in range(0, total):
        if(total_left > 0):
            rand_var = random.randint(0,1)
            if(rand_var == 1):
                total_left -= 1
            output.append(str(rand_var))
        else:
            output.append("0")
    return output

if(len(sys.argv) > 1 and  sys.argv[1].isdigit()):
    i = 0
    text_file = open("output.txt", "w")
    if(len(sys.argv) > 2 and sys.argv[2] == "1"):
        lost_stolen = lostOrStolenStatusBias(int(sys.argv[1]))
    else:
        lost_stolen = []
        for i in range(0, int(sys.argv[1])):
            lost_stolen.append(str(random.randint(0,1)))
    print(lost_stolen)
    while(i<int(sys.argv[1])):
        #Details Format
        #cardNo,dd/mm/yyyy/,mm/yyyy,lostOrStolenStatus,pin
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
        issue_string = random_day + "/" + random_month + "/" + random_issue_year
        expiry_string = random_month + "/" + random_expiry_year
        lost_stolen_status = lost_stolen[0]
        lost_stolen.pop(0)
        joined_string += "," + issue_string + "," + expiry_string + "," + lost_stolen_status + "," + joined_pin + "\n"
        text_file.write(joined_string)
        i += 1
    text_file.close()
else:
    print("Enter Correct Args - Num of Cards")