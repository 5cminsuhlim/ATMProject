#requires names python module
import names
import random
import sys
import decimal


# userCount: How many users to create
# cardCount: How many cards to create ( must be >userCount )
#  Bias: Limit total lost/stolen to < 10%


userCount = int(100)
cardCount = int(4000)
bias = "y"


if userCount > cardCount:
    print("Number of users must be equal or greater than number of cards.")
    sys.exit()

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

def leadingZero(value):
    value = int(value)
    if(value < 10):
        return_value = "0" + str(value)
        return return_value
    return str(value)

i = 0
users_file = open("../../../userlist.txt", "w")
ids = []
while(i<userCount):
    #Details Format
    #userID,fullName,balance
    current_id = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
    while current_id in ids:
        current_id = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
    ids.append(current_id)
    i += 1

for id in ids:
    joined_string = "{},{},{}\n".format("".join(id), names.get_full_name(), '{:.2f}'.format(decimal.Decimal(random.randrange(0, 10000000))/100))
    users_file.write(joined_string)
users_file.close()

i = 0
cards_file = open("../../../cardlist.txt", "w")
if bias == "y":
    lost_stolen = lostOrStolenStatusBias(cardCount)
    random.shuffle(lost_stolen)
else:
    lost_stolen = []
    for i in range(0, cardCount):
        lost_stolen.append(str(random.randint(0,1)))

j = 0
k = 0
while j < cardCount:
    #Details Format
    #cardNo,mm/yyyy/,mm/yyyy,lostOrStolenStatus,pin,userID
    current_random = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
    current_pin = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
    joined_pin = "".join(current_pin)
    joined_string = "".join(current_random)
    random_month = str(random.randint(1,12))
    random_month = leadingZero(random_month)
    random_issue_year = str(random.randint(2017,2019))
    random_expiry_year = str(random.randint(2020,2022))
    issue_string = random_month + "/" + random_issue_year
    expiry_string = random_month + "/" + random_expiry_year
    lost_stolen_status = lost_stolen[0]
    lost_stolen.pop(0)

    if k >= len(ids):
        k = 0
    joined_string += "," + issue_string + "," + expiry_string + "," + lost_stolen_status + "," + joined_pin + "," + "".join(ids[k]) + "\n"
    cards_file.write(joined_string)

    j += 1
    k += 1
cards_file.close()
print("Success")
