package Utility;

public class Matrix3x3 extends Matrix
{
	
	public Matrix3x3()
	{
		this.data = new double[3 * 3];
		this.rows = 3;
		this.cols = 3;
	}
	
	public Matrix3x3(double[] data)
	{
		this.data = data;
		this.rows = 3;
		this.cols = 3;
	}
	
	public Vector3i multiply(Vector3i vec3)
	{
		return new Vector3i(data[0] * vec3.x + data[1] * vec3.y + data[2] * vec3.z, data[3] * vec3.x + data[4] * vec3.y + data[5] * vec3.z, data[6] * vec3.x + data[7] * vec3.y + data[8] * vec3.z);
	}
	
	public Matrix3x3 multiply(Matrix3x3 mat3)
	{
		return new Matrix3x3(new double[] {data[0] * mat3.data[0] + data[1] * mat3.data[3] + data[2] * mat3.data[6], data[0] * mat3.data[1] + data[1] * mat3.data[4] + data[2] * mat3.data[7], data[0] * mat3.data[2] + data[1] * mat3.data[5] + data[2] * mat3.data[8], data[3] * mat3.data[0] + data[4] * mat3.data[3] + data[5] * mat3.data[6], data[3] * mat3.data[1] + data[4] * mat3.data[4] + data[5] * mat3.data[7], data[3] * mat3.data[2] + data[4] * mat3.data[5] + data[5] * mat3.data[8], data[6] * mat3.data[0] + data[7] * mat3.data[3] + data[8] * mat3.data[6], data[6] * mat3.data[1] + data[7] * mat3.data[4] + data[8] * mat3.data[7], data[6] * mat3.data[2] + data[7] * mat3.data[5] + data[8] * mat3.data[8]});
	}
}
