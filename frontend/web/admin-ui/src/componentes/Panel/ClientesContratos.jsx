import React, { useEffect, useState } from 'react';
import { Chart } from 'primereact/chart';
import Reportes from '../../servicios/Reportes';

function ClientesContratos() {
    const [clientes, setClientes] = useState([]);
    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});

    useEffect(() => {
        Reportes.getClientesContratos()
            .then((clientes) => {
                setClientes(clientes);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []); 

    useEffect(() => {
        if (clientes.length > 0) {

            clientes.sort((a, b) => b.cantidad_contratos - a.cantidad_contratos);

            const backgroundColors = clientes.map((cliente, index) => (
                index === 0 ? "#E8A477" : "#FCE6D8"
            ));
            const hoverBackgroundColors = clientes.map((cliente, index) => (
                index === 0 ? "#BC7547" : "#F4E0D3"
            ));

            const data = {
                labels: clientes.map(cliente => cliente.nombre.split(' ')[0] + " " + cliente.apellido_paterno),
                datasets: [
                    {
                        label: 'Cantidad de Contratos',
                        data: clientes.map(cliente => cliente.cantidad_contratos),
                        backgroundColor: backgroundColors,
                        hoverBackgroundColor: hoverBackgroundColors
                    }
                ]
            };

            const options = {
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltips: {
                        callbacks: {
                            label: function(tooltipItem, data) {
                                let label = data.labels[tooltipItem.index] || '';
                                if (label) {
                                    label += ': ' + data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                                }
                                return label;
                            }
                        }
                    }
                }
            };

            setChartData(data);
            setChartOptions(options);
        }
    }, [clientes]);

    return (
        <div className='bg-white rounded-lg shadow-xl w-full p-10 mr-4 mb-10'>
            <h2 className="mb-5 font-bold text-gray-700 text-lg">Clientes con mayores contratos</h2>
            <div>
                <Chart type="bar" data={chartData} options={chartOptions} className="w-full px-6" />
            </div>
        </div>
    );
}

export default ClientesContratos;
