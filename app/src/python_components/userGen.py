#requires names python module
import names
import random
import sys
import decimal
if(len(sys.argv) > 1 and  sys.argv[1].isdigit()):
	i = 0
	text_file = open("userlist", "w")
	ids = []
	while(i<int(sys.argv[1])):
        #Details Format
        #userID,fullName,balance
		current_id = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
		while current_id in ids:
			current_id = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
		ids.append(current_id)
		i += 1
		
	for id in ids:
		joined_string = "{},{},{}\n".format("".join(id), names.get_full_name(), '{:.2f}'.format(decimal.Decimal(random.randrange(0, 10000000))/100))
		text_file.write(joined_string)
	
	text_file.close()
else:
	print("Enter Correct Args - number of Users")