import React, { useEffect, useState } from 'react';
import { Chart } from 'primereact/chart';
import Reportes from '../../servicios/Reportes';

function ServiciosProveedores() {

    const [servicios, setServicios] = useState([]);
    const [totalProveedores, setTotalProveedores] = useState(0);

    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});

    useEffect(() => {
        Reportes.getServiciosProveedores()
            .then(servicios => {
                setServicios(servicios);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    useEffect(() => {
        if (servicios.length > 0) {

            servicios.sort((a, b) => b.cantidad_proveedores - a.cantidad_proveedores);

            const total = servicios.reduce((accumulator, servicio) => accumulator + servicio.cantidad_proveedores, 0);
            setTotalProveedores(total);

            const backgroundColors = servicios.map((servicio, index) => {
                return index === 0 ? "#E8A477" : "#FCE6D8";
            });

            const hoverBackgroundColors = servicios.map((servicio, index) => {
                return index === 0 ? "#BC7547" : "#F4E0D3";
            });

            const data = {
                labels: servicios.map(servicio => servicio.nombre),
                datasets: [
                    {
                        data: servicios.map(servicio => servicio.cantidad_proveedores),
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
    }, [servicios]);

    return (
        <div className='bg-white rounded-lg shadow-xl w-full p-10 mr-4 mb-10'>
            <h2 className="mb-5 font-bold text-gray-700 text-lg">Proveedores por servicio</h2>
            <div className='grid grid-cols-2 gap-4'>
                <Chart type="pie" data={chartData} options={chartOptions} className="w-full px-6" />
                <div>
                    <div className='text-start px-8 flex flex-wrap items-center justify-between'>
                        <h1 className='font-bold text-[#BC7547] text-lg'>Total</h1>
                        <p className='text-base'>{totalProveedores}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ServiciosProveedores;
