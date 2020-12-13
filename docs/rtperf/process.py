#!/usr/bin/env python3

import matplotlib as mpl
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import os
from pathlib import Path


def main():
	parent = Path(__file__).parent
	results = process(f"final NIO", parent / f"perf.finalnio.csv")

	fig1, ax1 = draw_graph([results], smoothed=True,   bytime=False, title="Smoothed dT by loop #")
	fig2, ax2 = draw_graph([results], smoothed=True,   bytime=True,  title="Smoothed dT by time")
	fig3, ax3 = draw_graph([results], smoothed=False,  bytime=False, title="Raw dT by loop #")
	fig4, ax4 = draw_graph([results], smoothed=False,  bytime=True,  title="Raw dT by time")
	plt.show()

def process(experiment, filename):
	print(f"Experiment \"{experiment}\":")
	print(f" - reading from file {filename}...")

	# read CSV from EV3
	df = pd.read_csv(filename, names=["index", "ts"])

	# start processing
	print(" - processing...")

	# get time zero (used for later correction)
	ts0 = df['ts'][0]

	# calculate how long individual loops took
	df['dt'] = df['ts'].diff(1) / 1000000.0

	# strip first row (always contains dT = NaN, because there's no -1-th value to compare the 0-th timestamp to)
	df = df.iloc[1:]

	# calculate time from experiment start in seconds
	df['t'] = (df['ts'] - ts0) / 1000000000.0

	# total experiment time
	millis = df['t'].max() * 1000.0

	# strip off first loop for analysis (very high spike, around 1.5 seconds)
	cutoff = df.iloc[1:]

	print(" - general info:")
	print(f"   - loops:  {len(df) + 1} loops")  # +1 is due to the stripped NaN row
	print(f"   - time:   {millis} ms")
	print(f" - analysis:")
	print(f"   - mean:   {df['dt'].mean()}")
	print(f"   - median: {df['dt'].median()}")
	print(f"   - min:    {df['dt'].min()}")
	print(f"   - max:    {df['dt'].max()}")
	print(f"   - stddev: {df['dt'].std()}")
	print(f" - analysis without first loop - that took {df['dt'][1]} ms:")
	print(f"   - mean:   {cutoff['dt'].mean()}")
	print(f"   - median: {cutoff['dt'].median()}")
	print(f"   - min:    {cutoff['dt'].min()}")
	print(f"   - max:    {cutoff['dt'].max()}")
	print(f"   - stddev: {cutoff['dt'].std()}")

	# store name in an attribute for easier access later
	df.name = experiment
	return df


def draw_graph(dfs, smoothed, bytime, title):
	# create new PyPlot
	print("Drawing graph...")
	fig, ax = plt.subplots(1, 1)

	# set title
	ax.set_title(title)

	# set axis names
	if bytime:
		ax.set_xlabel("Time from experiment start (s)")
	else:
		ax.set_xlabel("Loop number (-)")
	ax.set_ylabel("Loop duration (ms)")

	# set linear scales
	ax.set_xscale("linear")
	ax.set_yscale("linear")
	
	# draw zero lines
	ax.axhline(c='black')
	ax.axvline(c='black')
	
	# enable grid
	ax.grid(b=True, which='major', axis='both', color='gray', linestyle='-', visible=True, linewidth=0.50)
	ax.grid(b=True, which='minor', axis='both', color='gray', linestyle=':', visible=True, linewidth=0.25)
	ax.minorticks_on()

	# draw data
	for df in dfs:
		if bytime:
			x = df['t']
		else:
			x = df['index']
		if smoothed:
			y = df['dt'].rolling(10).mean()
		else:
			y = df['dt']
		ax.plot(x, y, label=df.name)

	leg = ax.legend(loc='best')
	leg.get_frame().set_alpha(0.5)
	return fig, ax


if __name__ == '__main__':
	main()
