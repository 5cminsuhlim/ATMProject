import random
import sys
if(len(sys.argv) > 1 and  sys.argv[1].isdigit()):
    i = 0
    text_file = open("output.txt", "w")
    while(i<int(sys.argv[1])):
        current_random = [str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9)),str(random.randint(0,9))]
        joined_string = "".join(current_random)
        joined_string = joined_string + "\n"
        text_file.write(joined_string)
        i += 1
    text_file.close()
else:
    print("Enter Correct Args - Num of Cards")