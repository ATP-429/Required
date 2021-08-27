package Utility;

public class Matrix4x4 extends Matrix
{
	
	public Matrix4x4()
	{
		this.data = new double[4 * 4];
		this.rows = 4;
		this.cols = 4;
	}
	
	public Matrix4x4(double[] data)
	{
		this.data = data;
		this.rows = 4;
		this.cols = 4;
	}
	
	public Vector4i multiply(Vector4i vec4)
	{
		return new Vector4i(data[0] * vec4.x + data[1] * vec4.y + data[2] * vec4.z + data[3] * vec4.w, data[4] * vec4.x + data[5] * vec4.y + data[6] * vec4.z + data[7] * vec4.w, data[8] * vec4.x + data[9] * vec4.y + data[10] * vec4.z + data[11] * vec4.w, data[12] * vec4.x + data[13] * vec4.y + data[14] * vec4.z + data[15] * vec4.w);
	}
	
	public Matrix4x4 multiply(Matrix4x4 mat4)
	{
		return new Matrix4x4(new double[] {data[0] * mat4.data[0] + data[1] * mat4.data[4] + data[2] * mat4.data[8] + data[3] * mat4.data[12], data[0] * mat4.data[1] + data[1] * mat4.data[5] + data[2] * mat4.data[9] + data[3] * mat4.data[13], data[0] * mat4.data[2] + data[1] * mat4.data[6] + data[2] * mat4.data[10] + data[3] * mat4.data[14], data[0] * mat4.data[3] + data[1] * mat4.data[7] + data[2] * mat4.data[11] + data[3] * mat4.data[15], data[4] * mat4.data[0] + data[5] * mat4.data[4] + data[6] * mat4.data[8] + data[7] * mat4.data[12], data[4] * mat4.data[1] + data[5] * mat4.data[5] + data[6] * mat4.data[9] + data[7] * mat4.data[13], data[4] * mat4.data[2] + data[5] * mat4.data[6] + data[6] * mat4.data[10] + data[7] * mat4.data[14], data[4] * mat4.data[3] + data[5] * mat4.data[7] + data[6] * mat4.data[11] + data[7] * mat4.data[15], data[8] * mat4.data[0] + data[9] * mat4.data[4] + data[10] * mat4.data[8] + data[11] * mat4.data[12], data[8] * mat4.data[1] + data[9] * mat4.data[5] + data[10] * mat4.data[9] + data[11] * mat4.data[13], data[8] * mat4.data[2] + data[9] * mat4.data[6] + data[10] * mat4.data[10] + data[11] * mat4.data[14], data[8] * mat4.data[3] + data[9] * mat4.data[7] + data[10] * mat4.data[11] + data[11] * mat4.data[15], data[12] * mat4.data[0] + data[13] * mat4.data[4] + data[14] * mat4.data[8] + data[15] * mat4.data[12], data[12] * mat4.data[1] + data[13] * mat4.data[5] + data[14] * mat4.data[9] + data[15] * mat4.data[13], data[12] * mat4.data[2] + data[13] * mat4.data[6] + data[14] * mat4.data[10] + data[15] * mat4.data[14], data[12] * mat4.data[3] + data[13] * mat4.data[7] + data[14] * mat4.data[11] + data[15] * mat4.data[15]});
	}
	
	public Matrix4x4 transpose()
	{
		return new Matrix4x4(new double[] {data[0], data[4], data[8], data[12], data[1], data[5], data[9], data[13], data[2], data[6], data[10], data[14], data[3], data[7], data[11], data[15]});
	}
	
	public Matrix4x4 clone()
	{
		return new Matrix4x4(new double[] {data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15]});
	}
}
