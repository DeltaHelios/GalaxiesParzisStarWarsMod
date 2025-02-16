package com.parzivail.aurek.model.nemi;

import java.util.Objects;

public final class NemiTexPos
{
	private final double u;
	private final double v;

	public NemiTexPos(double u, double v)
	{
		this.u = u;
		this.v = v;
	}

	public double u()
	{
		return u;
	}

	public double v()
	{
		return v;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (NemiTexPos)obj;
		return Double.doubleToLongBits(this.u) == Double.doubleToLongBits(that.u) &&
		       Double.doubleToLongBits(this.v) == Double.doubleToLongBits(that.v);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(u, v);
	}

	@Override
	public String toString()
	{
		return "NemiTexPos[" +
		       "u=" + u + ", " +
		       "v=" + v + ']';
	}
}
