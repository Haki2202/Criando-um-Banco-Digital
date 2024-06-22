import java.util.ArrayList;
import java.util.List;

abstract class Conta {
    protected String numero;
    protected double saldo;

    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0.0;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Depósito de R$ " + valor + " realizado na conta " + numero);
    }

    public abstract void sacar(double valor);

    public void transferir(Conta destino, double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            destino.depositar(valor);
            System.out.println("Transferência de R$ " + valor + " realizada da conta " + numero + " para conta " + destino.getNumero());
        } else {
            System.out.println("Saldo insuficiente para transferência da conta " + numero);
        }
    }
}

class ContaCorrente extends Conta {
    private double limiteChequeEspecial;

    public ContaCorrente(String numero, double limiteChequeEspecial) {
        super(numero);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) {
        if (saldo + limiteChequeEspecial >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado na conta corrente " + numero);
        } else {
            System.out.println("Saldo insuficiente para saque na conta corrente " + numero);
        }
    }
}

class ContaPoupanca extends Conta {
    private double taxaJuros;

    public ContaPoupanca(String numero, double taxaJuros) {
        super(numero);
        this.taxaJuros = taxaJuros;
    }

    @Override
    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado na conta poupança " + numero);
        } else {
            System.out.println("Saldo insuficiente para saque na conta poupança " + numero);
        }
    }

    public void aplicarJuros() {
        saldo *= (1 + taxaJuros);
        System.out.println("Juros aplicados na conta poupança " + numero);
    }
}

public class Banco {
    private List<Conta> contas;

    public Banco() {
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
        System.out.println("Conta " + conta.getNumero() + " adicionada ao banco");
    }

    public Conta buscarConta(String numero) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Banco banco = new Banco();

        ContaCorrente cc1 = new ContaCorrente("001", 1000);
        ContaPoupanca cp1 = new ContaPoupanca("002", 0.05);
        ContaCorrente cc2 = new ContaCorrente("003", 500);

        banco.adicionarConta(cc1);
        banco.adicionarConta(cp1);
        banco.adicionarConta(cc2);

        cc1.depositar(2000);
        cc1.sacar(500);
        cc1.transferir(cc2, 300);

        cp1.depositar(1500);
        cp1.aplicarJuros();

        System.out.println("Saldo final da conta corrente 1: R$ " + cc1.getSaldo());
        System.out.println("Saldo final da conta poupança 1: R$ " + cp1.getSaldo());
        System.out.println("Saldo final da conta corrente 2: R$ " + cc2.getSaldo());
    }
}