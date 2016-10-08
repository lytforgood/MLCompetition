# !/usr/bin/python
# coding:utf-8

import csv
import math
import copy

C_time_hh_max = '20'
C_time_hh = '08'
C_time_mi = '00'
C_speed = 15 # km/h
C_serv = 3
C_serv_fix = 5
C_load = 140
C_cheat = 10
C_penalty_tw = 5
C_penalty_base = 10
C_man=1000

def getmins(time_str):
	hh = time_str.split(':')[0]
	mi = time_str.split(':')[1]
	return (int(hh)-int(C_time_hh))*60+int(mi)-int(C_time_mi)
def dist(longi1, lati1, longi2, lati2):
	if longi1 is None or lati1 is None or longi2 is None or lati2 is None:
		return None
	else:
		delta_lati = (lati1 - lati2) / 2
		delta_longi = (longi1 - longi2) / 2
		tmp_val = math.sin(math.radians(delta_lati)) ** 2 + \
        	math.cos(math.radians(lati1)) * \
        	math.cos(math.radians(lati2)) * \
        	math.sin(math.radians(delta_longi)) ** 2
	return int(round(2 * math.asin(math.sqrt(tmp_val)) * 6378137 / (C_speed*1000/60)))
def serv(num_ord):
	return round(C_serv*math.sqrt(num_ord)+5) if num_ord>0 else 0

# ---------- loading original data ------------
# -- note: '1.csv','2.csv','3.csv','4.csv','5.csv' are the files we provide, please put the files in the correct path!
points = {}
csvfile = open('1.csv','rU')
lines = csv.reader(csvfile)
for line in lines:
	sInd,lng,lat = line
	if lng=='Lng':
		continue
	points.setdefault(sInd,[])
	points[sInd].extend([float(lng),float(lat)])
csvfile.close()
csvfile = file('2.csv','rU')
lines = csv.reader(csvfile)
for line in lines:
	sInd,lng,lat = line
	if lng=='Lng':
		continue
	points.setdefault(sInd,[])
	points[sInd].extend([float(lng),float(lat)])
csvfile.close()
csvfile = file('3.csv','rU')
lines = csv.reader(csvfile)
for line in lines:
	sInd,lng,lat = line
	if lng=='Lng':
		continue
	points.setdefault(sInd,[])
	points[sInd].extend([float(lng),float(lat)])
csvfile.close()
orders={}
csvfile = file('4.csv','rU')
lines = csv.reader(csvfile)
for line in lines:
	oid,tInd,fInd,num_ord = line
	if num_ord=='Num':
		continue
	orders.setdefault(oid,[])
	orders[oid].extend([tInd,fInd,getmins('08:00'),getmins('20:00'),int(num_ord),1])
csvfile.close()
csvfile = file('5.csv','rU')
lines = csv.reader(csvfile)
for line in lines:
	oid,tInd,fInd,tw_1,tw_2,num_ord = line
	if tw_1=='Pickup_time':
		continue
	orders.setdefault(oid,[])
	orders[oid].extend([tInd,fInd,getmins(tw_1),getmins(tw_2),int(num_ord),2])
csvfile.close()

# ------------- loading solution ------------
solution = {}
csvfile = file('/**/your_solution.csv','rU') # please input your solution path
lines = csv.reader(csvfile)
for line in lines:
	dInd,wInd,ariv,dept,num,oid = line
	solution.setdefault(dInd,[])
	solution[dInd].append([wInd,int(ariv),int(dept),int(num),oid])
csvfile.close()

# parameters for check
dmen = solution.keys()
nodeset_org = []
nodeset_org.extend(orders.keys()) 
nodeset_check = [] 
tmp_cheat = 0
costs = 0
if len(dmen)>C_man:
	print 'error: the maximum number of postman is out!'
for dInd in dmen:
	rNum = len(solution[dInd])
	if rNum%2 != 0:
		print 'error: pickup need to match delivery!'
		# break
	else:
		pickup_nodes = [] 
		delivery_nodes = []
		nInd_befr = None
		nInd_next = None
		nInd = None
		tmp_num = 0
		tmp_time = 0
		tmp_load = 0
		tmp_dist_in = 720
		for rInd in solution[dInd]:
			tmp_num += 1
			nInd = copy.deepcopy(solution[dInd][tmp_num-1])		
			if tmp_num==1 and tmp_num<len(solution[dInd]):
				nInd_befr = copy.deepcopy(nInd)
				if orders[nInd[4]][5]==2:
					nInd_befr[1]=0
					nInd_befr[2]=0
					for sInd in points.keys():
						# print sInd[0]
						if sInd[0]=='A':
							tmp_dist_minutes = dist(points[sInd][0],points[sInd][1],points[nInd[0]][0],points[nInd[0]][1])
							if tmp_dist_minutes<tmp_dist_in:
								tmp_dist_in=tmp_dist_minutes
								nInd_befr[0]=sInd
			else:
				nInd_befr = copy.deepcopy(solution[dInd][tmp_num-2])

			# check
			tmp_load += rInd[3]
			if tmp_load>C_load:
				print 'error: the maximum number of packages is out!'
			if rInd[1]-tmp_time<0 or rInd[2]<rInd[1]:
				print 'error: for one postman, the solution must be sorted by arrival time!'
				# break
			else:
				tmp_time = rInd[2]

			if rInd[3]>0:
				pickup_nodes.extend([rInd[4]])
				if len(pickup_nodes)!=len(set(pickup_nodes)) or rInd[3]!=orders[rInd[4]][4]:
					print 'error: one order must be picked one time!'
					# break
				if rInd[0]!=orders[rInd[4]][1]:
					print 'error: the pickup point of order not match original data!'
					# break
			elif rInd[3]<0:
				delivery_nodes.extend([rInd[4]])
				nodeset_check.extend([rInd[4]])
				if -rInd[3]!=orders[rInd[4]][4] or len(delivery_nodes)!=len(set(delivery_nodes)):
					print 'error: one order must be deliveried one time!'
					# break
				if rInd[0]!=orders[rInd[4]][0]:
					print 'error: the delivery point of order not match original data!'
					# break
				if len(set(delivery_nodes)-set(pickup_nodes))>0:
					print 'error: one order must be picked first, then delivery!'
			else:
				print 'error: the number of packages is zero!'
				# break
			if rInd[2]<orders[rInd[4]][2]:
				print 'error: departure time is earlier than the reqired earliest time!'
				# break

			# computing costs
			serv_time = int(max(orders[nInd[4]][2]-nInd[1],0)) if orders[nInd[4]][5]==2 and nInd[3]>0 else serv(-nInd[3])
			time_dist = nInd[1] if tmp_num==1 and orders[nInd[4]][5]==2 else dist(points[nInd_befr[0]][0],points[nInd_befr[0]][1],points[nInd[0]][0],points[nInd[0]][1])			
			penalty_serv = int(abs((nInd[2]-nInd[1])-serv_time)*C_penalty_base*(0 if orders[nInd[4]][5]==2 and nInd[3]>0 else 1))
			penalty_rout = int(max(tmp_dist_in-time_dist,0))*C_penalty_base if tmp_num==1 and orders[nInd[4]][5]==2 else int(abs(nInd[1]-nInd_befr[2]-time_dist))*C_penalty_base
			if penalty_serv>0 or penalty_rout>0:
				tmp_cheat+=1
				if tmp_cheat>C_cheat:
					print 'error: do not cheat!'
			penalty_tw = int(max(nInd[1]-orders[nInd[4]][2],0))*C_penalty_tw if nInd[3]>0 and orders[nInd[4]][5]==2 else int(max(nInd[1]-orders[nInd[4]][3],0))*C_penalty_tw
			
			tmp_cost = serv_time+time_dist + penalty_rout + penalty_serv+penalty_tw	
			costs = costs + tmp_cost

if len(set(nodeset_check))!=len(set(nodeset_org)):
	print 'error: the number of orders not match original data!'
print 'cost: %d, postman: %d' %(costs,len(dmen))

