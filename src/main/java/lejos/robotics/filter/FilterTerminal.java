package lejos.robotics.filter;

import lejos.robotics.SampleProvider;

public class FilterTerminal {

  private SampleProvider source;
  private int index = 0;
  private float[] sample; 

  public FilterTerminal(SampleProvider source) {
    this.source = source;
    sample = new float[source.sampleSize()];
  }
  
  public void setIndex(int index) {
    if (index <=0 && index < source.sampleSize()) {
      this.index = index;
    }
    else throw new  IllegalArgumentException("Index exceeds sample size");
  }
  
  public boolean isFalse() {
    source.fetchSample(sample, 0);
    if (sample[index] == 0) return true;
    return false;
  }
  
  public boolean isTrue() {
    return !isFalse();
  }
  
  public float fetch() {
    source.fetchSample(sample, 0);
    return sample[index];
  }

}
