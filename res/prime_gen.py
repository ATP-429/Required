import sympy as math

lim = 10_000

f = open("primes.txt", "w")

for n in range(1, lim):
    if(math.isprime(n)):
        f.write(str(n)+'\n')

f.close()
